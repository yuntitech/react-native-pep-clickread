
Pod::Spec.new do |s|
  s.name         = "RNPepClickread"
  s.version      = "1.0.0"
  s.summary      = "PEPClickread"
  s.description  = "PEPClickread SDK"
  s.homepage     = "https://github.com/yuntitech/react-native-pep-clickread"
  s.license      = "MIT"
  s.author       = { "ZYXiao" => "304983615@qq.com" }
  s.platform     = :ios, "9.0"
  s.source       = { :git => "https://github.com/yuntitech/RNPepClickread.git", :tag => "1.0.0" }
  s.source_files  = "ios/RNPepClickread.{h,m}"
  s.requires_arc = true

  # s.dependency "PEPNetworking", :git => 'https://github.com/PEPDigitalPublishing/PEPNetworking.git'
  # s.dependency "PEPBigData", :git => 'https://github.com/PEPDigitalPublishing/PEPBigData.git'
  # s.dependency "PEPReaderSDK", :git => 'https://github.com/PEPDigitalPublishing/PEPReaderSDK.git'
  # s.dependency "PEPiFlyMSC", :git => 'https://github.com/PEPDigitalPublishing/PEPiFlyMSC.git'
  s.dependency "SSZipArchive"

end



  
