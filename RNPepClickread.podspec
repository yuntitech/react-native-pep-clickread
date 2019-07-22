require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = "RNPepClickread"
  s.version      = package['version']
  s.summary      = package['description']
  s.description  = package['description']
  s.homepage     = "https://github.com/yuntitech/react-native-pep-clickread"
  s.license      = "MIT"
  s.author       = { "ZYXiao" => "304983615@qq.com" }
  s.platform     = :ios, "9.0"
  s.source       = { :git => "https://github.com/yuntitech/RNPepClickread.git", :tag => package['version'] }
  s.source_files  = "ios/RNPepClickread.{h,m}"
  s.requires_arc = true

  s.dependency "PEPNetworking", :git => 'https://github.com/PEPDigitalPublishing/PEPNetworking.git'
  s.dependency "PEPBigData", :git => 'https://github.com/PEPDigitalPublishing/PEPBigData.git'
  s.dependency "PEPReaderSDK", :git => 'https://github.com/PEPDigitalPublishing/PEPReaderSDK.git'
  s.dependency "PEPiFlyMSC", :git => 'https://github.com/PEPDigitalPublishing/PEPiFlyMSC.git'
  s.dependency "SSZipArchive"

end



  
