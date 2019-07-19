
Pod::Spec.new do |s|
  s.name         = "RNPepClickread"
  s.version      = "1.0.0"
  s.summary      = "RNPepClickread"
  s.description  = <<-DESC
                  RNPepClickread
                  DESC
s.homepage     = "https://github.com/yuntitech/react-native-pep-clickread"
s.license      = "MIT"
# s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
s.author             = { "author" => "author@domain.cn" }
s.platform     = :ios, "9.0"
s.source       = { :git => "https://github.com/yuntitech/RNPepClickread.git", :tag => "1.0.0" }
s.source_files  = "ios/**/*.{h,m}"
s.requires_arc = true

s.dependency "React"
s.dependency "PEPNetworking"
s.dependency "PEPBigData"
s.dependency "PEPReaderSDK"
s.dependency "PEPiFlyMSC"
s.dependency "SSZipArchive"

end



  
