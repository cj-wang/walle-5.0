call mvn install:install-file -Dfile=walle-core-5.0-20141017.jar -DgroupId=cn.walle -DartifactId=walle-core -Dversion=5.0-20141017 -Dpackaging=jar

call mvn install:install-file -Dfile=walle-easyui-5.0-20150104.jar -DgroupId=cn.walle -DartifactId=walle-easyui -Dversion=5.0-20150104 -Dpackaging=jar

call mvn install:install-file -Dfile=walle-platform-5.0-20140802.jar -DgroupId=cn.walle -DartifactId=walle-platform -Dversion=5.0-20140802 -Dpackaging=jar

call mvn install:install-file -Dfile=walle-system-5.0-20141229.jar -DgroupId=cn.walle -DartifactId=walle-system -Dversion=5.0-20141229 -Dpackaging=jar

call mvn install:install-file -Dfile=walle-json-1.0.jar -DgroupId=cn.walle -DartifactId=walle-json -Dversion=1.0 -Dpackaging=jar

pause
