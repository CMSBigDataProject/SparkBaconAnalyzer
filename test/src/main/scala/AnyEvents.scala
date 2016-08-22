trait AnyEvents {
  def getInfo: baconhep.TEventInfo
  def getElectron: java.util.List[baconhep.TElectron]
  def getMuon: java.util.List[baconhep.TMuon]
  def getTau: java.util.List[baconhep.TTau]
  def getPhoton: java.util.List[baconhep.TPhoton]
  def getPV: java.util.List[baconhep.TVertex]
  def getAK4CHS: java.util.List[baconhep.TJet]
  def getAK8CHS: java.util.List[baconhep.TJet]
  def getAddAK8CHS: java.util.List[baconhep.TAddJet]
  def getCA15CHS: java.util.List[baconhep.TJet]
  def getAddCA15CHS: java.util.List[baconhep.TAddJet]
  def getAK4Puppi: java.util.List[baconhep.TJet]
  def getCA8Puppi: java.util.List[baconhep.TJet]
  def getAddCA8Puppi: java.util.List[baconhep.TAddJet]
  def getCA15Puppi: java.util.List[baconhep.TJet]
  def getAddCA15Puppi: java.util.List[baconhep.TAddJet]
}
