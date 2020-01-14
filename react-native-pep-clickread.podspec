require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = "react-native-pep-clickread"
  s.version      = package['version']
  s.summary      = package['description']
  s.description  = package['description']
  s.homepage     = package['homepage']
  s.license      = package['license']
  s.author       = package['author']
  s.platform     = :ios, "9.0"
  s.source       = { :git => "https://github.com/yuntitech/react-native-pep-clickread.git", :tag => "master" }
  s.source_files = "ios/**/*.{h,m}"
  s.requires_arc = true

  s.dependency 'React'

  # https://github.com/PEPDigitalPublishing/PEPReaderSDK/blob/master/PEPReaderSDK.podspec#L31
  s.dependency 'SSZipArchive', '2.2.2'
end

  