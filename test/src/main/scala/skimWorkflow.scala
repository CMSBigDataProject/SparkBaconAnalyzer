import org.apache.avro.mapred.AvroKey
import org.apache.hadoop.io.NullWritable
import org.apache.spark.SparkContext

object SkimWorkflow {

    def c = 9

    def test_eventNumbers(event: Events) = {
    	event.getInfo.getEvtNum
    }

    //def test(event: Events) = {
    //	event.getTau.maxBy(_.pt)
    //}

    // RDDs
    def DY(sc: SparkContext) = sc.newAPIHadoopFile[AvroKey[Events], NullWritable, MyKeyInputFormat[Events]]("/user/HEP/DYJetsToLL*/*.avro").map(_._1.datum)
    def QCD(sc: SparkContext) = sc.newAPIHadoopFile[AvroKey[Events], NullWritable, MyKeyInputFormat[Events]]("/user/HEP/QCD_HT*/*.avro").map(_._1.datum)
    def T(sc: SparkContext) = sc.newAPIHadoopFile[AvroKey[Events], NullWritable, MyKeyInputFormat[Events]]("/user/HEP/ST_t*/*.avro").map(_._1.datum)  
    def W(sc: SparkContext) = sc.newAPIHadoopFile[AvroKey[Events], NullWritable, MyKeyInputFormat[Events]]("/user/HEP/WJetsToLNu*/*.avro").map(_._1.datum)
    def WW(sc: SparkContext) = sc.newAPIHadoopFile[AvroKey[Events], NullWritable, MyKeyInputFormat[Events]]("/user/HEP/WW_13TeV_pythia8/*.avro").map(_._1.datum)

    // main filters
    def filterTau(t: baconhep.TTau) = {	
    	t.pt >= 10 && Math.abs(t.eta) < 2.3
    }

    // TLorentzVector
    case class TLorentzVector() {
      //def SetXYZT(x: Double, y: Double, z: Double, t: Double) {
      //   fP.SetXYZ(x, y, z);	     
      //   SetT(t);
      //}
      //def SetXYZM(x: Double, y: Double, z: Double, m: Double) {
      // if(m >= 0) SetXYZT( x, y, z, Math.sqrt(x*x+y*y+z*z+m*m) )
      //  else SetXYZT( x, y, z, math.sqrt( Math.max((x*x+y*y+z*z-m*m), 0.)))
      //	    }  	 
      //	def SetPtEtaPhiM(pt: Double, eta: Double, phi: Double, m: Double) {
      //  var Pt: Double = pt
      //  var Eta: Double = eta
      //  var Phi: Double = phi
      // var M: Double = m
      // }
      //def add(that: TLorentzVector): TLorentzVector = 
    }	 

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
                                                                                                                                                                                                 
      val chHadIso  = Math.max(photon.chHadIso  - rho*phoEffArea(photon.scEta, 0), 0.)
      val neuHadIso = Math.max(photon.neuHadIso - rho*phoEffArea(photon.scEta, 1), 0.)
      val phoIso    = Math.max(photon.gammaIso  - rho*phoEffArea(photon.scEta, 2), 0.)

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
                                                                                                                                                                                                                                  
      val chHadIso  = Math.max(photon.chHadIso  - rho*phoEffArea(photon.scEta, 0), 0.)
      val neuHadIso = Math.max(photon.neuHadIso - rho*phoEffArea(photon.scEta, 1), 0.)
      val phoIso    = Math.max(photon.gammaIso  - rho*phoEffArea(photon.scEta, 2), 0.)

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
    def passVeto(iEta: Double, iPhi: Double, idR: Double) = {
        val pMatch = false
        //for( i1 <- 0 to iVetoes.size) {
          //val pDEta = iEta - iVetoes[i1].Eta
          //val pDPhi = iPhi - iVetoes[i1].Phi
          //if(Math.abs(pDPhi) > 2*Math.pi-Math.abs(pDPhi))  pDPhi = 2*Math.pi-Math.abs(pDPhi)
          //if(Math.sqrt(pDPhi*pDPhi+pDEta*pDEta) < idR && iVetoes[i1].Pt < 0) pMatch = true
        //} 
	pMatch
    } 
}	      
