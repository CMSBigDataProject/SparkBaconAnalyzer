// TLorentzVector
trait LorentzVectorMethods {
  def pt: Double
  def phi: Double
  def eta: Double
  def m: Double

  def px = Math.abs(pt)*Math.cos(phi)
  def py = Math.abs(pt)*Math.sin(phi)
  def pz = Math.abs(pt)*Math.sinh(eta)
  def e = {
    if(m >= 0) Math.sqrt(px*px+py*py+pz*pz+m*m)
    else Math.sqrt(Math.max(px*px+py*py+pz*pz-m*m,0))
  }

  def +(that: LorentzVectorMethods) = {
    val px = this.px + that.px
    val py = this.py + that.py
    val pz = this.pz + that.pz
    val e = this.e + that.e
    val (pt, phi, pta, m) = LorentzVectorMethods.setptphietam(px, py, pz, e)
    LorentzVector(pt, phi, eta, m)
  }

  def DeltaR(that: LorentzVectorMethods) = {
    val deta = this.eta - that.eta
    val dphi = if(this.phi - that.phi >= Math.PI) this.phi - that.phi - Math.PI
               else if(this.phi - that.phi < -Math.PI) this.phi - that.phi + Math.PI
               else this.phi - that.phi
    Math.sqrt(deta*deta + dphi*dphi)
  }
}

object LorentzVectorMethods {
  def setptphietam(px: Double, py: Double, pz: Double, e: Double) = {
    val pt = Math.sqrt(px*px + py*py)
    val p = Math.sqrt(px*px + py*py + pz*pz)
    val m = Math.sqrt(e*e - px*px - py*py - pz*pz)
    val eta = 0.5*Math.log((p + pz)/(p - pz))
    val phi = Math.atan2(py, px)
    (pt, phi, eta, m)
  }
}
