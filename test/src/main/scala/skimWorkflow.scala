import org.apache.avro.mapred.AvroKey
import org.apache.hadoop.io.NullWritable
import org.apache.spark.SparkContext
import org.apache.spark.sql.Row
import org.apache.spark.rdd._
import scala.collection.JavaConversions._
import scala.language.postfixOps
import MakeRow._
import org.dianahep.histogrammar._
import org.dianahep.histogrammar.json._

object SkimWorkflow {

    // RDDs
    def SingleElectron(sc: SparkContext) : RDD[DataEvents] = { 
       sc.hadoopConfiguration.set("avro.schema.input.key",DataEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/SingleElectron*/*.avro",classOf[MyKeyInputFormat[DataEvents]], classOf[AvroKey[DataEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)  
    }
    def MET(sc: SparkContext) : RDD[DataEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",DataEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/METRun2015D_16Dec2015_v1/*.avro",classOf[MyKeyInputFormat[DataEvents]], classOf[AvroKey[DataEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def SinglePhoton(sc: SparkContext) : RDD[DataEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",DataEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/SinglePhoton*/*.avro",classOf[MyKeyInputFormat[DataEvents]], classOf[AvroKey[DataEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def	QCD100to200(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/QCD_HT100to200*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def QCD200to300(sc: SparkContext) : RDD[MCEvents] = { 
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/QCD_HT200to300*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def QCD300to500(sc: SparkContext) : RDD[MCEvents] = { 
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/QCD_HT300to500*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def QCD500to700(sc: SparkContext) : RDD[MCEvents] = { 
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/QCD_HT500to700*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def QCD700to1000(sc: SparkContext) : RDD[MCEvents] = { 
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/QCD_HT700to1000*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def QCD1000to1500(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/QCD_HT1000to1500*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum) 
    }
    def QCD1500to2000(sc: SparkContext) : RDD[MCEvents] = { 
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/QCD_HT1500to2000*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def QCD2000toInf(sc: SparkContext) : RDD[MCEvents] = { 
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/QCD_HT2000toInf*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def	W100to200(sc: SparkContext) : RDD[MCEvents] = { 
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/WJetsToLNu_HT_100to200_13TeV*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def W200to400(sc: SparkContext) : RDD[MCEvents] = { 
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/WJetsToLNu_HT_200to400_13TeV*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def W400to600(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/WJetsToLNu_HT_400to600_13TeV*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def W600toInf(sc: SparkContext) : RDD[MCEvents] = { 
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/WJetsToLNu_HT_600toInf_13TeV*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def Z100to200(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/ZJetsToNuNu_HT_100to200_13TeV/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def Z200to400(sc: SparkContext) : RDD[MCEvents] = { 
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/ZJetsToNuNu_HT_200to400_13TeV*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def Z400to600(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/ZJetsToNuNu_HT_400to600_13TeV*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    } 
    def Z600toInf(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/ZJetsToNuNu_HT_600toInf_13TeV*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    } 
    def	DY100to200(sc: SparkContext) : RDD[MCEvents] = { 
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/DYJetsToLL_M_50_HT_100to200_13TeV_2/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def DY200to400(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/DYJetsToLL_M_50_HT_200to400_13TeV_2/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def DY400to600(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/DYJetsToLL_M_50_HT_400to600_13TeV_2/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def DY600toInf(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/DYJetsToLL_M_50_HT_600toInf_13TeV_2/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def G100to200(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/GJets_HT_100to200_13TeV/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def G200to400(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/GJets_HT_200to400_13TeV/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def G400to600(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/GJets_HT_400to600_13TeV/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def G600toInf(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/GJets_HT_600toInf_13TeV/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def Ttantitop(sc: SparkContext) : RDD[MCEvents] = { 
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/ST_t_channel_antitop_4f_inclusiveDecays_13TeV_*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def Tttop(sc: SparkContext) : RDD[MCEvents] = { 
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/ST_t_channel_top_4f_inclusiveDecays_13TeV_*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def TtWantitop(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/ST_tW_antitop_5f_inclusiveDecays_13TeV_*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def TtWtop(sc: SparkContext) : RDD[MCEvents] = { 
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/ST_tW_top_5f_inclusiveDecays_13TeV_*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def TT(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/TTJets_13TeV*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def TTG(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/TTGJets_13TeV*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def TTZ(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/TTZToLLNuNu*/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def WW(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/WW_13TeV_pythia8/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def WZ(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/WZ_13TeV_pythia8/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }
    def ZZ(sc: SparkContext) : RDD[MCEvents] = {
       sc.hadoopConfiguration.set("avro.schema.input.key",MCEvents.getClassSchema.toString)
       sc.newAPIHadoopFile("/user/HEP/ZZ_13TeV_pythia8/*.avro",classOf[MyKeyInputFormat[MCEvents]], classOf[AvroKey[MCEvents]], classOf[NullWritable],sc.hadoopConfiguration).map(_._1.datum)
    }

    // X-sec
    val xsQCD100to200 = 27500000
    val xsQCD200to300 = 1735000
    val xsQCD300to500 = 367000
    val xsQCD500to700 = 29370
    val xsQCD700to1000 = 6524
    val xsQCD1000to1500 = 1064
    val xsQCD1500to2000 = 121.5
    val xsQCD2000toInf = 25.42

    val xsWJetsToLNu100to200 = 1343
    val xsWJetsToLNu200to400 = 359.6
    val xsWJetsToLNu400to600 = 48.85
    val xsWJetsToLNu600toInf = 18.91

    val xsDYJetsToLL100to200 = 148
    val xsDYJetsToLL200to400 = 40.94
    val xsDYJetsToLL400to600 = 5.497
    val xsDYJetsToLL600toInf = 2.193
  
    val xsZJetsToNuNu100to200 = 280.5
    val xsZJetsToNuNu200to400 = 77.7
    val xsZJetsToNuNu400to600 = 10.71
    val xsZJetsToNuNu600toInf = 4.098

    val xsTtantitop = 44.0802
    val xsTttop = 26.2343
    val xsTtWantitop = 35.6
    val xsTtWtop = 35.6
    val xsTZ = 0.0758

    val xsTTZ = 0.2529
    val xsTTG = 3.697
    val xsTT = 831.76

    val xsWW = 118.7
    val xsWZ = 47.2
    val xsZZ = 31.8

    val xsGJets100to200 = 9235
    val xsGJets200to400 = 2298
    val xsGJets400to600 = 277.6
    val xsGJets600toInf = 93.47

    // Constants
    val muonMass = 0.105658369
    val electronMass = 0.000510998910
    val photonMass = 0
    val CSVL = 0.605

    // Check GenInfo (for MC only)
    def loadGenInfo(event: MCEvents, xs: Double, weight: Double) = {
       (xs*1000*event.getGenEvtInfo.weight)/weight
    }

    case class InfoVars(var runNum: Long, var lumiSec: Long, var evtNum: Long, var metfilter: Long, var scale1fb: Double, var evtWeight: Double, var pfmet: Double, var pfmetphi: Double, var puppet: Double, var puppetphi: Double, var fakepfmet: Double, var fakepfmetphi: Double, var fakepuppet: Double, var fakepuppetphi: Double)
    case class GenEvtInfoVars(var genVPt: Double, var genVPhi: Double)
    case class MuonVars(var pt: Double, var eta: Double, var phi: Double, var m: Double)
    case class ElectronVars(var pt: Double, var eta: Double, var phi: Double, var m: Double) 
    case class TauVars(var pt: Double, var eta: Double, var phi: Double) 
    case class PhotonVars(var NLoose: Int, var NMedium: Int, var pt: Double, var eta: Double, var phi: Double) 
    case class JetVars(var N: Int,  var NdR15: Int, var NbtagLdR15: Int, var pt: Double, var eta: Double, var phi: Double, var m: Double, var csv: Double, var CHF: Double, var NHF: Double, var NEMF: Double, var mindPhi: Double, var mindFPhi: Double) 
    case class VJetVars(var N: Int, var pt: Double, var eta: Double, var phi: Double, var m: Double, var csv: Double, var CHF: Double, var NHF: Double, var NEMF: Double, var tau21: Double, var tau32: Double, var msd: Double, var minsubcsv: Double, var maxsubcsv: Double)
    case class AllVars(var infovars: InfoVars = null, var genevtinfovars: GenEvtInfoVars = null, var muonvars: MuonVars = null, var electronvars: ElectronVars = null, var tauvars: TauVars = null, var photonvars: PhotonVars = null, var jetvars: JetVars = null, var vjetvars: VJetVars = null)

    def runMonoX(event: AnyEvents, xsec: Double, nevts: Double) = {

      // trigger information missing
      // lepton SFs missing
      // btag SFs missing
      // trigger effs missing

      val filteredMuons = event.getMuon.filter(filterMuon)
      val filteredElectrons = event.getElectron.filter(filterElectron(_, event.getInfo.rhoIso))
      val filteredTaus = event.getTau.filter(filterTau)
      val filteredPhotons = event.getPhoton.filter(filterPhoton(_, event.getInfo.rhoIso))
      val filteredJets = event.getAK4Puppi.filter(filterJet)
      val filteredVJets = event.getCA15Puppi.filter(filterVJet)

      val allvars = AllVars(null, null, null, null, null, null, null, null)

      allvars.infovars = InfoVars(0, 0, 0, 0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
      allvars.infovars.runNum = event.getInfo.runNum
      allvars.infovars.lumiSec = event.getInfo.lumiSec	      
      allvars.infovars.evtNum = event.getInfo.evtNum
      allvars.infovars.metfilter = event.getInfo.metFilterFailBits
      allvars.infovars.pfmet = event.getInfo.pfMETC
      allvars.infovars.pfmetphi = event.getInfo.pfMETCphi
      allvars.infovars.puppet = event.getInfo.puppETC
      allvars.infovars.puppetphi = event.getInfo.puppETCphi
      allvars.infovars.fakepfmet = event.getInfo.pfMETC
      allvars.infovars.fakepfmetphi = event.getInfo.pfMETCphi
      allvars.infovars.fakepuppet = event.getInfo.puppETC
      allvars.infovars.fakepuppetphi = event.getInfo.puppETCphi

      val vpuppet = LorentzVector(event.getInfo.puppETC,0,event.getInfo.puppETCphi,0)
      val vpfmet = LorentzVector(event.getInfo.pfMETC,0,event.getInfo.pfMETCphi,0)
      
      allvars.genevtinfovars = GenEvtInfoVars(0.0, 0.0)

      var vmuon = LorentzVector(0,0,0,0)
      if (!filteredMuons.isEmpty) {
        allvars.muonvars = MuonVars(0.0, 0.0, 0.0, 0.0)
        val m = filteredMuons.maxBy(_.pt)
        allvars.muonvars.pt = m.pt
	allvars.muonvars.eta = m.eta
        allvars.muonvars.phi = m.phi
        allvars.muonvars.m = muonMass
	vmuon = LorentzVector(m.pt, m.eta, m.phi, muonMass)
      }

      var velectron = LorentzVector(0,0,0,0)
      if (!filteredElectrons.isEmpty) {
        allvars.electronvars = ElectronVars(0.0, 0.0, 0.0, 0.0)
        val e = filteredElectrons.maxBy(_.pt)
        allvars.electronvars.pt = e.pt
        allvars.electronvars.eta = e.eta
        allvars.electronvars.phi = e.phi
        allvars.electronvars.m = electronMass
	velectron = LorentzVector(e.pt, e.eta, e.phi, electronMass)
      }

      if (!filteredTaus.isEmpty) {
        allvars.tauvars = TauVars(0.0, 0.0, 0.0)
        val t = filteredTaus.maxBy(_.pt)
        allvars.tauvars.pt = t.pt
        allvars.tauvars.eta = t.eta
	allvars.tauvars.phi = t.phi
      }

      if (!filteredPhotons.isEmpty) {
        allvars.photonvars = PhotonVars(0, 0, 0.0, 0.0, 0.0)
        val p = filteredPhotons.maxBy(_.pt)
        allvars.photonvars.pt = p.pt
        allvars.photonvars.eta = p.eta
        allvars.photonvars.phi = p.phi
	allvars.photonvars.NMedium = filteredPhotons.size
      }

      var vjet = LorentzVector(0, 0, 0, 0)

      if (!filteredVJets.isEmpty) {
        allvars.vjetvars = VJetVars(0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
	val v = filteredVJets.maxBy(_.pt)
        val vadd = event.getAddCA15Puppi.find(puppijet => event.getCA15Puppi.apply(puppijet.index.toInt) == v) match {
         case Some(puppijet) => puppijet
         case None => throw new Exception()
       }

        allvars.vjetvars.N = filteredVJets.size
        allvars.vjetvars.pt = v.pt
        allvars.vjetvars.eta = v.eta
        allvars.vjetvars.phi = v.phi
        allvars.vjetvars.m = v.mass
        allvars.vjetvars.csv = v.csv
        allvars.vjetvars.CHF = v.chHadFrac
        allvars.vjetvars.NHF = v.neuHadFrac
        allvars.vjetvars.NEMF = v.neuEmFrac
        allvars.vjetvars.tau21 = vadd.tau2/vadd.tau1
        allvars.vjetvars.tau32 = vadd.tau3/vadd.tau2
        allvars.vjetvars.msd = vadd.mass_sd0
        allvars.vjetvars.minsubcsv = Math.min(vadd.sj1_csv, vadd.sj2_csv)
        allvars.vjetvars.maxsubcsv = Math.max(Math.max(vadd.sj1_csv, vadd.sj2_csv),Math.max(vadd.sj3_csv, vadd.sj4_csv))
        vjet = LorentzVector(v.pt,v.eta,v.phi,v.mass)
      }
      
      var jet = LorentzVector(0,0,0,0)

      if (!filteredJets.isEmpty) {
        allvars.jetvars = JetVars(0, 0, 0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        val j = filteredJets.maxBy(_.pt)
	var pdPhi = 999.99
	var pdFPhi = 999.99

        allvars.jetvars.pt = j.pt
        allvars.jetvars.eta = j.eta
        allvars.jetvars.phi = j.phi
        allvars.jetvars.m = j.mass
	allvars.jetvars.csv = j.csv
        allvars.jetvars.CHF = j.chHadFrac
        allvars.jetvars.NHF = j.neuHadFrac
        allvars.jetvars.NEMF = j.neuEmFrac

	pdPhi = if(Math.acos(Math.cos(allvars.infovars.puppetphi-j.phi)) < pdPhi) Math.acos(Math.cos(allvars.infovars.puppetphi-j.phi)) else pdPhi
	pdFPhi = if(allvars.infovars.fakepuppet>0 && Math.acos(Math.cos(allvars.infovars.fakepuppetphi-j.phi)) < pdFPhi) Math.acos(Math.cos(allvars.infovars.fakepuppetphi-j.phi)) else pdPhi
	allvars.jetvars.mindPhi = pdPhi
	allvars.jetvars.mindFPhi = pdFPhi

        allvars.jetvars.N = filteredJets.size
        allvars.jetvars.NdR15 = filteredJets.filter(dR(_,vjet,1.5)).size
        allvars.jetvars.NbtagLdR15 = filteredJets.filter(dR(_,vjet,1.5)).filter(j => Math.abs(j.eta) < 2.5).filter(j => j.csv > CSVL).size

        jet = LorentzVector(j.pt,j.eta,j.phi,j.mass)
      }

      if(allvars.infovars.pfmet > 200.0 || allvars.infovars.puppet > 200.0 || allvars.infovars.fakepuppet > 200.0 || allvars.infovars.fakepfmet > 200.0){
        List(allvars)
      }
      else{
	List()
      }
    }


    //if(event.AK4Puppi.filter(filterJet).maxBy(_.pt).pt > 250) true

    // Json and Lumi
     val runlumiLookup: Map[Long, Seq[(Long, Long)]] = Json.parse(new java.util.Scanner(new java.io.FileInputStream("Cert_13TeV_16Dec2015ReReco_Collisions15_25ns_JSON_v2.txt")).useDelimiter("\\A").next) match {
       case Some(JsonObject(runlumi @ _*)) =>
         runlumi map {
	   case (JsonString(run), JsonArray(lumi @ _*)) =>
             (java.lang.Long.parseLong(run), lumi map {
               case JsonArray(JsonInt(start), JsonInt(end)) => (start, end)
               case _ => throw new Exception
             })
           case _ => throw new Exception
         } toMap
      case _ => throw new Exception
     }

    def eventInLumi(lumiSec: Long, lumiPair: Seq[(Long, Long)]): Boolean =
      lumiPair exists {case (start, end) => start <= lumiSec && lumiSec <= end}

    def goodEvent(event: AnyEvents) = {
       val lRun = new Tuple2(event.getInfo.runNum,event.getInfo.lumiSec)
       val lumiPair: Seq[(Long, Long)] = runlumiLookup.getOrElse(event.getInfo.runNum, Seq[(Long, Long)]()) 
       eventInLumi(event.getInfo.lumiSec, lumiPair)
    }

    // In Spark:
    // rdd
    // rdd.filter(goodEvent).map(runMonoX)

    // filters from defined variables
    def filterMET(i: InfoVars) = {
    	i.pfmet > 170 || i.puppet > 170 || i.fakepuppet > 170 || i.fakepfmet > 170
    }

    // main filters
    def filterMuon(m: baconhep.TMuon) = {
        m.pt >= 10 && Math.abs(m.eta) < 2.4 && passMuonLooseSel(m)
    }

    def filterElectron(e: baconhep.TElectron, iRho: Double) = {
        e.pt >= 10 && Math.abs(e.eta) < 2.5 && passEleSel(e,iRho)
    }

    def filterTau(t: baconhep.TTau) = {
    	val iV = Vector.empty
	var iVetoes = iV :+ LorentzVector(0,0,0,0)
    	passVeto(t.eta,t.phi,0.4,iVetoes)
    	t.pt >= 10 && Math.abs(t.eta) < 2.3 && passTauSel(t) 
    }

    def filterPhoton(p: baconhep.TPhoton, iRho: Double) = {
        val iV = Vector.empty
        var iVetoes = iV :+ LorentzVector(0,0,0,0)
        passVeto(p.eta,p.phi,0.4,iVetoes)
        //p.pt >= 15 && Math.abs(p.eta) < 2.5 && passPhoLooseSel(p,iRho)
        p.pt >= 175 && Math.abs(p.eta) < 1.4442 && passPhoMediumSel(p,iRho)
    }

    def filterJet(j: baconhep.TJet) = {
        j.pt >= 30 && Math.abs(j.eta) < 4.5 && passJetLooseSel(j)
    }

    def filterVJet(j: baconhep.TJet) = {
        j.pt >= 150 && Math.abs(j.eta) < 2.5 && passJetLooseSel(j)
    }

    // small filters
    def dR(j: baconhep.TJet, vj: LorentzVector, R: Double) = { 
        val jet = LorentzVector(j.pt, j.eta, j.phi, j.mass)
	jet.DeltaR(vj) > R
    }


    // filling variables
    def fillTau(t: baconhep.TTau) = {
        LorentzVector(t.pt, t.eta, t.phi, t.m)
    }

    abstract class selTaus(val pt: Double, val phi: Double, val eta: Double, val m: Double) extends Product

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

    case class LorentzVector(pt: Double, phi: Double, eta: Double, m: Double) extends LorentzVectorMethods with Ordered[LorentzVector] {
      // return 0 if the same, negative if this < that, positive if this > that
      def compare (that: LorentzVector) = {
        if (this.pt == that.pt)
          0
        else if (this.pt > that.pt)
          1
        else
	 -1
      }
    } 

    //Object LorentzVector {
    //def frompxpypze(px: Double, py: Double, pz: Double, e: Double) = {
    //    val (pt, phi, eta, m) = LorentzVectorMethods.setptphietam(px, py, pz, e) 
    //    LorentzVector(pt, phi, eta, m)
    //  }
    //}

    // Filters from MonoXUtils

    // Muon POG selection ID   
    def kPOGLooseMuon  =  1
    def kPOGMediumMuon =  2
    def kPOGTightMuon  =  4
    def kPOGSoftMuon   =  8
    def kPOGHighPtMuon = 16

    def passJet04Sel(jet: baconhep.TJet) = {
      // Loose PFjet ID
      if(jet.neuHadFrac >= 0.99) false
      else if(jet.neuEmFrac  >= 0.99) false
      else if(jet.nParticles <= 1) false
      else if(jet.muonFrac   >= 0.8) false
      else if((Math.abs(jet.eta)<2.4) && ((jet.chHadFrac == 0) || (jet.nCharged  == 0) || (jet.chEmFrac  >= 0.99))) false
      // PU Jet ID
      if(0 <= Math.abs(jet.eta) && Math.abs(jet.eta) < 2.5  && jet.mva < -0.63) false
      else if(2.5  <= Math.abs(jet.eta) && Math.abs(jet.eta) < 2.75 && jet.mva < -0.60) false
      else if(2.75 <= Math.abs(jet.eta) && Math.abs(jet.eta) < 3    && jet.mva < -0.55) false
      else if(3    <= Math.abs(jet.eta) && Math.abs(jet.eta) < 5    && jet.mva < -0.45) false
      true
    }

    def passJetLooseSel(jet: baconhep.TJet) = {
      // Loose PFJet ID
      if(jet.neuHadFrac >= 0.99) false
      else if(jet.neuEmFrac  >= 0.99) false
      else if(jet.nParticles <= 1) false
      else if((Math.abs(jet.eta)<2.4) && ((jet.chHadFrac == 0) || (jet.nCharged  == 0) || (jet.chEmFrac  >= 0.99))) false
      else true
    }

    def passJetTightLepVetoSel(jet: baconhep.TJet) ={
      // Tight PFJet ID
      // https://twiki.cern.ch/twiki/bin/viewauth/CMS/JetID#Recommendations_for_13_TeV_data
      if(jet.neuHadFrac >= 0.90) false  
      else if(jet.neuEmFrac  >= 0.90) false
      else if(jet.nParticles <= 1) false
      else if(jet.muonFrac   >= 0.8) false
      else if((Math.abs(jet.eta)<2.4) && ((jet.chHadFrac == 0) || (jet.nCharged  == 0) || (jet.chEmFrac  >= 0.90))) false
      else true
    }

    def eleEffArea(eta: Double) = {
      // effective area for PU correction
      // (see slide 4 of https://indico.cern.ch/event/370494/contribution/2/material/slides/0.pdf)
      if(Math.abs(eta) >= 0.0 && Math.abs(eta) < 0.8) 0.1752
      else if(Math.abs(eta) >= 0.8 && Math.abs(eta) < 1.3) 0.1862
      else if(Math.abs(eta) >= 1.3 && Math.abs(eta) < 2.0) 0.1411
      else if(Math.abs(eta) >= 2.0 && Math.abs(eta) < 2.2) 0.1534
      else if(Math.abs(eta) >= 2.2 && Math.abs(eta) < 2.3) 0.1903
      else if(Math.abs(eta) >= 2.3 && Math.abs(eta) < 2.4) 0.2243
      else                                                 0.2687
    }

    def phoEffArea(eta: Double, t: Int) =	 {
      // effective area for PU correction
      // (https://twiki.cern.ch/twiki/bin/view/CMS/CutBasedPhotonIdentificationRun2#Pointers_for_PHYS14_selection_im)
      val KchHad  = 0
      val KneuHad = 1
      val Kphoton = 2
      if(t == KchHad){
        if(Math.abs(eta) >= 0.0        && Math.abs(eta) < 1.0)   0.0157
        else if(Math.abs(eta) >= 1.0   && Math.abs(eta) < 1.479) 0.0143
        else if(Math.abs(eta) >= 1.479 && Math.abs(eta) < 2.0)   0.0115
        else if(Math.abs(eta) >= 2.0   && Math.abs(eta) < 2.2)   0.0094
        else if(Math.abs(eta) >= 2.2   && Math.abs(eta) < 2.3)   0.0095
        else if(Math.abs(eta) >= 2.3   && Math.abs(eta) < 2.4)   0.0068
        else                                                     0.0053   
      }
      else if(t == KneuHad){
        if(Math.abs(eta) >= 0.0        && Math.abs(eta) < 1.0)   0.0143
        else if(Math.abs(eta) >= 1.0   && Math.abs(eta) < 1.479) 0.0210
        else if(Math.abs(eta) >= 1.479 && Math.abs(eta) < 2.0)   0.0147
        else if(Math.abs(eta) >= 2.0   && Math.abs(eta) < 2.2)   0.0082
        else if(Math.abs(eta) >= 2.2   && Math.abs(eta) < 2.3)   0.0124
        else if(Math.abs(eta) >= 2.3   && Math.abs(eta) < 2.4)   0.0186
        else                                                     0.0320   
      }
       else if(t == Kphoton){
        if(Math.abs(eta) >= 0.0        && Math.abs(eta) < 1.0)   0.0725
        else if(Math.abs(eta) >= 1.0   && Math.abs(eta) < 1.479) 0.0604
        else if(Math.abs(eta) >= 1.479 && Math.abs(eta) < 2.0)   0.0320
        else if(Math.abs(eta) >= 2.0   && Math.abs(eta) < 2.2)   0.0512
        else if(Math.abs(eta) >= 2.2   && Math.abs(eta) < 2.3)   0.0766
        else if(Math.abs(eta) >= 2.3   && Math.abs(eta) < 2.4)   0.0949
        else                                                     0.1160   
      }   
      else                                                            0
    }

    def phoEffAreaHighPt(eta: Double, t: Int) = {
      // effective area for PU correction
      // (https://twiki.cern.ch/twiki/bin/view/CMS/CutBasedPhotonIdentificationRun2#Pointers_for_PHYS14_selection_im)
      val Kphoton = 2 // is this constant already defined?
      if(t == Kphoton){
        if(Math.abs(eta) >= 0.0        && Math.abs(eta) < 1.0)   0.17
        else if(Math.abs(eta) >= 1.0   && Math.abs(eta) < 1.479) 0.14
        else if(Math.abs(eta) >= 1.479 && Math.abs(eta) < 2.0)   0.11
        else if(Math.abs(eta) >= 2.0   && Math.abs(eta) < 2.2)   0.14
        else                                                     0.22
      } 
      else                                                          0
    }

    def passEleSel(electron: baconhep.TElectron, rho: Double) = {
      // Phys14 PU20 bx25 cut-based veto ID
      // https://twiki.cern.ch/twiki/bin/view/CMS/CutBasedElectronIdentificationRun2#Working_points_for_PHYS14_sample
      if(electron.isConv) false
      val iso = electron.chHadIso + Math.max( 0.0,(electron.gammaIso + electron.neuHadIso - rho*eleEffArea(electron.eta)) )
      if(Math.abs(electron.scEta)<1.479) {
        if(iso >= 0.126*(electron.pt))                                       false
        if(electron.sieie                  >= 0.01140)                       false
        if(Math.abs(electron.dEtaIn)       >= 0.01520)                       false
        if(Math.abs(electron.dPhiIn)       >= 0.21600)                       false
        if(electron.hovere                 >= 0.18100)                       false
        if(Math.abs(1.0 - electron.eoverp) >= 0.20700*(electron.ecalEnergy)) false
        if(Math.abs(electron.d0)           >= 0.05640)                       false
        if(Math.abs(electron.dz)           >= 0.47200)                       false
        if(electron.nMissingHits           >  2)                             false
      } else {
        if(iso >= 0.144*(electron.pt))                                       false
        if(electron.sieie                  >= 0.03520)                       false
        if(Math.abs(electron.dEtaIn)       >= 0.01130)                       false
        if(Math.abs(electron.dPhiIn)       >= 0.23700)                       false
        if(electron.hovere                 >= 0.11600)                       false
        if(Math.abs(1.0 - electron.eoverp) >= 0.17400*(electron.ecalEnergy)) false
        if(Math.abs(electron.d0)           >= 0.22200)                       false
        if(Math.abs(electron.dz)           >= 0.92100)                       false
        if(electron.nMissingHits           >  3)                             false
      }
      true
    } 

    def eleIso(electron: baconhep.TElectron, rho: Double) = {
      electron.chHadIso + Math.max( 0.0,(electron.gammaIso + electron.neuHadIso - rho*eleEffArea(electron.eta)) )
    }

    def passEleTightSel(electron: baconhep.TElectron, rho: Double) = {
      // Phys14 PU20 bx25 cut-based veto ID
      // https://twiki.cern.ch/twiki/bin/view/CMS/CutBasedElectronIdentificationRun2#Working_points_for_PHYS14_sample
      // if(electron.pt < 40 || Math.abs(electron.eta) > 2.5) false

      if(electron.isConv) false
      val iso = electron.chHadIso + Math.max( 0.0,(electron.gammaIso + electron.neuHadIso - rho*eleEffArea(electron.eta)) )
      if(Math.abs(electron.scEta)<1.479) {
        if(iso >= 0.0354*(electron.pt))                                           false
        else if(electron.sieie                  >= 0.01010)                       false
        else if(Math.abs(electron.dEtaIn)       >= 0.00926)                       false
        else if(Math.abs(electron.dPhiIn)       >= 0.03360)                       false
        else if(electron.hovere                 >= 0.05970)                       false
        else if(Math.abs(1.0 - electron.eoverp) >= 0.01200*(electron.ecalEnergy)) false
        else if(Math.abs(electron.d0)           >= 0.01110)                       false
        else if(Math.abs(electron.dz)           >= 0.04660)                       false
        else if(electron.nMissingHits       >  2)                                 false
      } else {
        if(iso >= 0.0646*(electron.pt))                                           false
        if(electron.sieie                       >= 0.02790)                       false
        else if(Math.abs(electron.dEtaIn)       >= 0.00724)                       false
        else if(Math.abs(electron.dPhiIn)       >= 0.09180)                       false
        else if(electron.hovere                 >= 0.06150)                       false
        else if(Math.abs(1.0 - electron.eoverp) >= 0.00999*(electron.ecalEnergy)) false
        else if(Math.abs(electron.d0)           >= 0.03510)                       false
        else if(Math.abs(electron.dz)           >= 0.41700)                       false
        else if(electron.nMissingHits           >  1)                             false
      }
      true
    } 

    def passMuonLooseSel(muon: baconhep.TMuon) = {
      // PF-isolation with Delta-beta correction                                                                                                                                                     
      val iso = muon.chHadIso + Math.max(muon.neuHadIso + muon.gammaIso - 0.5*(muon.puIso), 0)
      ((muon.pogIDBits & kPOGLooseMuon) != 0) && (iso < 0.12*(muon.pt))
    }

    def passMuonTightSel(muon: baconhep.TMuon) = {
      // PF-isolation with Delta-beta correction                                                                                                                                                                                     
      val iso = muon.chHadIso + Math.max(muon.neuHadIso + muon.gammaIso - 0.5*(muon.puIso), 0)
      ((muon.pogIDBits & kPOGTightMuon) != 0) && (iso < 0.12*(muon.pt))
    }

    def passTauSel(t: baconhep.TTau) = ((t.hpsDisc.toInt & 2) != 0) && (t.rawIso3Hits <= 5)

    def passPhoLooseSel(photon: baconhep.TPhoton, rho: Double) = { 
      // Loose photon ID (https://twiki.cern.ch/twiki/bin/view/CMS/CutBasedPhotonIdentelse ificationRun2#PHYS14_selections_PU20_bunch_cro)                                             
      // if !(photon.passElectronVeto) false  // conversion safe electron veto                                                                                                                                           \
                                                                                                                                                                                                 
      val chHadIso  = Math.max(photon.chHadIso  - rho*phoEffArea(photon.scEta, 0), 0.0)
      val neuHadIso = Math.max(photon.neuHadIso - rho*phoEffArea(photon.scEta, 1), 0.0)
      val phoIso    = Math.max(photon.gammaIso  - rho*phoEffArea(photon.scEta, 2), 0.0)

      if(Math.abs(photon.scEta) <= 1.479) {
        if(photon.sthovere      > 0.05)                                     false
        else if(photon.sieie    > 0.0103)                                   false
        else if(chHadIso        > 2.44)                                     false
        else if(neuHadIso       > 2.57 + Math.exp(0.0044*photon.pt+0.5809)) false
        else if(phoIso          > 1.92 + 0.0043*photon.pt)                  false
      }
      else {
        if(photon.sthovere      > 0.05)                                     false
        else if(photon.sieie    > 0.0277)                                   false
        else if(chHadIso        > 1.84)                                     false
        else if(neuHadIso       > 4.00 + Math.exp(0.0040*photon.pt+0.9402)) false
        else if(phoIso          > 2.15 + 0.0041*photon.pt)                  false
      }
      true
    }

    def passPhoMediumSel(photon: baconhep.TPhoton, rho: Double) = {
      // Medium photon ID (https://twiki.cern.ch/twiki/bin/view/CMS/CutBasedPhotonIdentelse ificationRun2#PHYS14_selections_PU20_bunch_cro)                                                                                                
      // if !(photon.passElectronVeto) false  // conversion safe electron veto                                                                                                                                           \
                                                                                                                                                                                                                                  
      val chHadIso  = Math.max(photon.chHadIso  - rho*phoEffArea(photon.scEta, 0), 0.0)
      val neuHadIso = Math.max(photon.neuHadIso - rho*phoEffArea(photon.scEta, 1), 0.0)
      val phoIso    = Math.max(photon.gammaIso  - rho*phoEffArea(photon.scEta, 2), 0.0)

      if(Math.abs(photon.scEta) <= 1.479) {
        if(photon.sthovere      > 0.05)                                     false
        else if(photon.sieie    > 0.0100)                                   false
        else if(chHadIso        > 1.31)                                     false
        else if(neuHadIso       > 0.60 + Math.exp(0.0044*photon.pt+0.5809)) false
        else if(phoIso          > 1.33 + 0.0043*photon.pt)                  false
      }
      else {
        if(photon.sthovere      > 0.05)                                     false
        else if(photon.sieie    > 0.0267)                                   false
        else if(chHadIso        > 1.25)                                     false
        else if(neuHadIso       > 1.65 + Math.exp(0.0040*photon.pt+0.9402)) false
        else if(phoIso          > 1.02 + 0.0041*photon.pt)                  false
      }
      true
    }

    // TOOLS
    def passVeto(iEta: Double, iPhi: Double, idR: Double, iVetoes: Vector[LorentzVector]) = {
      var pMatch = false
      for( i1 <- 0 until iVetoes.size) {
        val pDEta = iEta - iVetoes(i1).eta
        var pDPhi = iPhi - iVetoes(i1).phi
        if(Math.abs(pDPhi) > 2*Math.PI-Math.abs(pDPhi)) pDPhi = 2*Math.PI-Math.abs(pDPhi) 
        if(Math.sqrt(pDPhi*pDPhi+pDEta*pDEta) < idR && iVetoes(i1).pt < 0) pMatch = true
      } 
      pMatch
    }

}	      
