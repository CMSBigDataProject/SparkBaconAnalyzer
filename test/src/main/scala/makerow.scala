import scala.language.experimental.macros
import scala.reflect.macros.Context
import scala.reflect.runtime.universe.Type
import scala.reflect.runtime.universe.WeakTypeTag

object MakeRow {
  def create[TYPE <: Product](args: Any*): TYPE = macro createImpl[TYPE]

  def createImpl[TYPE : c.WeakTypeTag](c: Context)(args: c.Expr[Any]*): c.Expr[TYPE] = {
    import c.universe._
    val rowClass = weakTypeOf[TYPE]

    val constructorParams = rowClass.declarations.collectFirst {
      case m: MethodSymbol if (m.isPrimaryConstructor) => m
    }.get.paramss.head

    val numArgs = constructorParams.size
    if (args.size != numArgs)
      throw new IllegalArgumentException(s"Wrong number of arguments for $rowClass: found ${args.size}, should be $numArgs")

    val castArgs = (constructorParams zip args) map {case (param, arg) =>
      val tpe = param.typeSignature
      q"""$arg.asInstanceOf[$tpe]"""
    }

    val cases = constructorParams.zipWithIndex map {case (param, i) =>
      val name = stringToTermName(param.asTerm.name.decodedName.toString)
      cq"""$i => $name"""
    }

    c.Expr[TYPE](q"""
      new $rowClass(..$castArgs) {
          def canEqual(that: Any) = that.isInstanceOf[$rowClass]
          def productArity = $numArgs
          def productElement(n: Int) = n match {
            case ..$cases
            case _ => throw new java.lang.IndexOutOfBoundsException(n.toString)
          }
      }
    """)
  }
}
