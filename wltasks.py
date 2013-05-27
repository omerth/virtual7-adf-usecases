from java.util.zip import ZipInputStream
from java.io import FileInputStream
from java.io import FileOutputStream
from java.io import InputStream
from java.io import BufferedReader
from java.io import InputStreamReader
import dbexts
import time

def wlCreateCred(mMap, mKey, mUser, mPassword):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Create credentials ' + mMap + ' ' + mKey + '\n'
	try:
		createCred(map=mMap, key=mKey, user=mUser, password=mPassword)
	except Exception, e:
		print e

def wlDestroyCred(mMap, mKey):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Destroy credentials ' + mMap + ' ' + mKey + '\n'
	try:
		deleteCred(map=mMap, key=mKey)
	except Exception, e:
		print e

def wlCreateJMSServerAQ(mServers):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Create jmsserver SystemModule-AQ' + '\n'
	try:
		mServersAr = []
		for target in mServers.split(";"):
			mServersAr.append(ObjectName(target))
	
		cd('/')
		cmo.createJMSServer('JMSServer-AQ')
	
		cd('/Deployments/JMSServer-AQ')
		set('Targets', jarray.array(mServersAr, ObjectName))
	
		cd('/')
		cmo.createJMSSystemResource('SystemModule-AQ')
	
		cd('/SystemResources/SystemModule-AQ')
		set('Targets', jarray.array(mServersAr, ObjectName))
	
		cmo.createSubDeployment('Subdeployment-JMSServer-AQ')
	
		cd('/SystemResources/SystemModule-AQ/SubDeployments/Subdeployment-JMSServer-AQ')
		set('Targets', jarray.array([ObjectName('com.bea:Name=JMSServer-AQ,Type=JMSServer')], ObjectName))
	except Exception, e:
		print e

def wlDestroyJMSServerAQ():
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Destroy jmsserver SystemModule-AQ' + '\n'
	try:
		cd('/')
		cmo.destroyJMSSystemResource(getMBean('/SystemResources/SystemModule-AQ'))
		cmo.destroyJMSServer(getMBean('/Deployments/JMSServer-AQ'))
	except Exception, e:
		print e

def wlCreateForeignServer(mAppName, mDatasourceName):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Create foreignserver ForeignServer-' + mAppName + '\n'
	try:
		mAppName = mAppName.upper()
		
		cd('/JMSSystemResources/SystemModule-AQ/JMSResource/SystemModule-AQ')
		cmo.createForeignServer('ForeignServer-' + mAppName)
		
		cd('/JMSSystemResources/SystemModule-AQ/JMSResource/SystemModule-AQ/ForeignServers/ForeignServer-' + mAppName)
		cmo.setSubDeploymentName('Subdeployment-JMSServer-AQ')
		
		cmo.setInitialContextFactory('oracle.jms.AQjmsInitialContextFactory')
		cmo.unSet('JNDIPropertiesCredentialEncrypted')
		cmo.createJNDIProperty('datasource')
		
		cd('/JMSSystemResources/SystemModule-AQ/JMSResource/SystemModule-AQ/ForeignServers/ForeignServer-' + mAppName + '/JNDIProperties/datasource')
		cmo.setValue('jdbc/' + mDatasourceName)
		
		cd('/JMSSystemResources/SystemModule-AQ/JMSResource/SystemModule-AQ/ForeignServers/ForeignServer-' + mAppName)
		cmo.createForeignDestination('ForeignDestination-' + mAppName)
		
		cd('/JMSSystemResources/SystemModule-AQ/JMSResource/SystemModule-AQ/ForeignServers/ForeignServer-' + mAppName + '/ForeignDestinations/ForeignDestination-' + mAppName)
		cmo.setLocalJNDIName('jms/' + mAppName + 'Queue')
		cmo.setRemoteJNDIName('Queues/' + mAppName + '_QUEUE')
		
		cd('/JMSSystemResources/SystemModule-AQ/JMSResource/SystemModule-AQ/ForeignServers/ForeignServer-' + mAppName)
		cmo.createForeignConnectionFactory('ForeignConnectionFactory-' + mAppName)
		
		cd('/JMSSystemResources/SystemModule-AQ/JMSResource/SystemModule-AQ/ForeignServers/ForeignServer-' + mAppName + '/ForeignConnectionFactories/ForeignConnectionFactory-' + mAppName)
		cmo.setLocalJNDIName('jms/' + mAppName + 'CF')
		cmo.setRemoteJNDIName('XAQueueConnectionFactory')
	except Exception, e:
		print e

def wlDestroyForeignServer(mAppName):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Destroy foreignserver ForeignServer-' + mAppName + '\n'
	try:
		mAppName = mAppName.upper()
		cd('/JMSSystemResources/SystemModule-AQ/JMSResource/SystemModule-AQ')
		cmo.destroyForeignServer(getMBean('/JMSSystemResources/SystemModule-AQ/JMSResource/SystemModule-AQ/ForeignServers/ForeignServer-' + mAppName))
	except Exception, e:
		print e

def wlCreateDatasource(mDatasourceName, mDatasourceUser, mDatasourcePassword, mDatasourceUrl, mTargets):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Create datasource ' + mDatasourceName + '\n'
	try:
		mTargetsAr = []
		for target in mTargets.split(";"):
			mTargetsAr.append(ObjectName(target))
		
		cd('/')
		cmo.createJDBCSystemResource(mDatasourceName)
		
		cd('/JDBCSystemResources/' + mDatasourceName + '/JDBCResource/' + mDatasourceName)
		cmo.setName(mDatasourceName)
		
		cd('/JDBCSystemResources/' + mDatasourceName + '/JDBCResource/' + mDatasourceName + '/JDBCDataSourceParams/' + mDatasourceName)
		set('JNDINames', jarray.array([String('jdbc/' + mDatasourceName)], String))
		
		cd('/JDBCSystemResources/' + mDatasourceName + '/JDBCResource/' + mDatasourceName + '/JDBCDriverParams/' + mDatasourceName)
		cmo.setUrl(mDatasourceUrl)
		cmo.setDriverName('oracle.jdbc.OracleDriver')
		cmo.setPassword(mDatasourcePassword)
		
		cd('/JDBCSystemResources/' + mDatasourceName + '/JDBCResource/' + mDatasourceName + '/JDBCConnectionPoolParams/' + mDatasourceName)
		cmo.setTestTableName('SQL SELECT 1 FROM DUAL\r\n\r\n')
		
		cd('/JDBCSystemResources/' + mDatasourceName + '/JDBCResource/' + mDatasourceName + '/JDBCDriverParams/' + mDatasourceName + '/Properties/' + mDatasourceName)
		cmo.createProperty('user')
		
		cd('/JDBCSystemResources/' + mDatasourceName + '/JDBCResource/' + mDatasourceName + '/JDBCDriverParams/' + mDatasourceName + '/Properties/' + mDatasourceName + '/Properties/user')
		cmo.setValue(mDatasourceUser)
		
		cd('/JDBCSystemResources/' + mDatasourceName + '/JDBCResource/' + mDatasourceName + '/JDBCDataSourceParams/' + mDatasourceName)
		cmo.setGlobalTransactionsProtocol('EmulateTwoPhaseCommit')
		
		cd('/SystemResources/' + mDatasourceName)
		
		set('Targets', jarray.array(mTargetsAr, ObjectName))
	except Exception, e:
		print e

def wlDestroyDatasource(mDatasourceName):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Destroy datasource ' + mDatasourceName + '\n'
	try:
		cd('/')
		cmo.destroyJDBCSystemResource(getMBean('/SystemResources/' + mDatasourceName))
	except :
		print "Unexpected error: ", sys.exc_info()[0]

def wlCreateMetadataLabel(mAppName, mTargets, mLabel):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Create metadata label for ' + mAppName + '\n'
	for target in mTargets.split(";"):
		try:
			createMetadataLabel(application=mAppName, server=ObjectName(target).getKeyProperty("Name"), name=mLabel)
		except Exception, e:
			print e

def wlDeployMDS(mAppName, mTargets , mPath , mRepositoryName , mRepositoryJNDI):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Deploy MDS application ' + mAppName + '\n'
	try:
		mTargetsAr = []
		for target in mTargets.split(";"):
			mTargetsAr.append(ObjectName(target).getKeyProperty("Name"))
		
		mTargetsStr = ",".join(mTargetsAr)
		
		archive = getMDSArchiveConfig(fromLocation=mPath)
		archive.setAppMetadataRepository(repository=mRepositoryName, partition=mAppName, type='DB', jndi=mRepositoryJNDI)
		archive.save()
		
		progress = deploy(appName=mAppName, path=mPath, upload='true', targets=mTargetsStr, block='true')
		
		print progress.isCompleted() 
		
		print '\n ### ### ###: Status of Deploy \n'
		progress.printStatus()
	except Exception, e:
		print e

def wlDeploy(mAppName, mTargets , mPath, mLibraryModule='true'):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Deploy ' + mAppName + '\n'
	try:	
		mTargetsStr = ""
		if(len(mTargets) > 0):
			mTargetsAr = []
			for target in mTargets.split(";"):
				mTargetsAr.append(ObjectName(target).getKeyProperty("Name"))
		
			mTargetsStr = ",".join(mTargetsAr) 
		
		progress = deploy(appName=mAppName, path=mPath, libraryModule=mLibraryModule, upload='true', targets=mTargetsStr, block='true')
		
		print progress.isCompleted() 
		print '\n ### ### ###: Status of Deploy \n'
		progress.printStatus()
	except Exception, e:
		print e

def wlUndeploy(mAppName, mTargets, mLibraryModule, mLibSpecVersion, mLibImplVersion):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Undeploy ' + mAppName + '\n'
	try:
		mTargetsStr = ""
		if(len(mTargets) > 0):
			mTargetsAr = []
			for target in mTargets.split(";"):
				mTargetsAr.append(ObjectName(target).getKeyProperty("Name"))
		
			mTargetsStr = ",".join(mTargetsAr)
		
		progress = undeploy(appName=mAppName, targets=mTargetsStr, block='true', libraryModule=mLibraryModule, libSpecVersion=mLibSpecVersion, libImplVersion=mLibImplVersion)
		
		print progress.isCompleted() 
		print '\n ### ### ###: Status of Undeploy \n'
		progress.printStatus()
	except Exception, e:
		print e

def wlImportMetadata(mAppName , mTargets , mPath):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Importing metadata' + mPath + '\n'
	for target in mTargets.split(";"):
		try:
			mTarget = ObjectName(target).getKeyProperty("Name")
			importMetadata(application=mAppName, server=mTarget, fromLocation=mPath, docs="/**", remote=true)
		except Exception, e:
			print e

def wlImportWebcenterResource(mAppName, mTargets, mPath, mResType, mOwrCD):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Importing webcenter resource ' + mPath + '\n'
	for target in mTargets.split(";"):
		try:
			mTarget = ObjectName(target).getKeyProperty("Name")
			importWebCenterResource(appName=mAppName, server=mTarget, fileName=mPath, resourceType=mResType, overwriteContentDirectory=int(mOwrCD))
		except Exception, e:
			print e

def wlExportWebcenterResource(mAppName, mTargets, mPath, mResType, mResGUID, mExpCD):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Exporting webcenter resource ' + mPath + '\n'
	for target in mTargets.split(";"):
		try:
			mTarget = ObjectName(target).getKeyProperty("Name")
			exportWebCenterResource(appName=mAppName, server=mTarget, fileName=mPath, resourceType=mResType, resourceGUID=mResGUID, exportContentDirectory=int(mExpCD))
		except Exception, e:
			print e

def wlImportWebcenterSpaces(mAppName, mTargets, mPath, mImpCust, mImpSec, mImpData, mForceOffline, mParentSpace, mOwrCD):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Importing space ' + mPath + '\n'
	for target in mTargets.split(";"):
		try:
			mTarget = ObjectName(target).getKeyProperty("Name")
			importGroupSpaces(appName=mAppName, server=mTarget, fileName=mPath, importCustomizations=int(mImpCust), importSecurity=int(mImpSec), importData=int(mImpData), forceOffline=int(mForceOffline), parentSpace=mParentSpace, overwriteContentDirectory=int(mOwrCD))
		except Exception, e:
			print e

def wlExportWebcenterSpaces(mAppName, mTargets, mPath, mSpaces, mForceOffline, mExpCD):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Exporting space ' + mPath + '\n'
	for target in mTargets.split(";"):
		try:
			mTarget = ObjectName(target).getKeyProperty("Name")
			exportGroupSpaces(appName=mAppName, server=mTarget, fileName=mPath, names=mSpaces, forceOffline=int(mForceOffline), exportContentDirectory=int(mExpCD))
		except Exception, e:
			print e

def wlExportWebcenterSpaceTemplates(mAppName, mTargets, mPath, mSpaces, mExpCD):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Exporting space template ' + mPath + '\n'
	for target in mTargets.split(";"):
		try:
			mTarget = ObjectName(target).getKeyProperty("Name")
			exportGroupSpaceTemplates(appName=mAppName, server=mTarget, fileName=mPath, names=mSpaces, exportContentDirectory=int(mExpCD))
		except Exception, e:
			print e
def wlStartServers(mTargets):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Starting servers' + mTargets + '\n'
	for target in mTargets.split(";"):
		try:		
			mTarget = ObjectName(target).getKeyProperty("Name")
			start(name=mTarget)
			print '\n Started server ' + mTarget + '\n'
		except Exception, e:
			print e

def wlStopServers(mTargets):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	print '\n Stopping servers' + mTargets + '\n'
	for target in mTargets.split(";"):
		try:		
			mTarget = ObjectName(target).getKeyProperty("Name")
			shutdown(name=mTarget, entityType='Server', ignoreSessions='true', timeOut=1000, force='true');
			print '\n Stopped server ' + mTarget + '\n'
		except Exception, e:
			print e

  
def unzipFile(zipFile, outputFolder) :
	zis = ZipInputStream(FileInputStream(zipFile))
	ze = zis.getNextEntry()
	while ze != None :
		fileName = ze.getName()
		newFile = File(outputFolder + File.separator + fileName)
		print("file unzip : " + str(newFile.getAbsoluteFile()))
		File(newFile.getParent()).mkdirs()
		fos = FileOutputStream(newFile)         
		len = zis.read()
		while len > 0 :
			fos.write(len)
			len = zis.read()
		fos.close()
		ze = zis.getNextEntry()
	zis.closeEntry()
	zis.close()

	
def getUpdateVersions(mDBVersion, mUpdateDir) :
	if os.path.exists(mUpdateDir) :
		allUpdateScripts = os.listdir(mUpdateDir)
		versions = []
		dbVersionInt = getVersionNumberAsInteger(mDBVersion)
		for updateScript in allUpdateScripts :
			if(updateScript.startswith("update_") and updateScript.endswith(".sql")):
				version = updateScript
				version = version.strip()
				version = version[:-4]
				version = version.split('_')[1]
				versionInt = getVersionNumberAsInteger(version)
				if versionInt > dbVersionInt :
					versions.append(version)
		return versions
	
def getVersionNumberAsInteger(scriptVersion) :
	version = ''
	for i in range(0, 3) :
		version += scriptVersion.split('.')[i]
	return int(version)

def generateDBEXTSConfig(mUser, mPassword, mUrl, mDBEXTSConfig):
	f = open(mDBEXTSConfig, 'a')

	print >> f, "[jdbc]"
	print >> f, "name=" + mUser
	print >> f, "url=" + mUrl
	print >> f, "user=" + mUser
	print >> f, "pwd=" + mPassword
	print >> f, "driver=oracle.jdbc.driver.OracleDriver"
	print >> f, "datahandler=com.ziclix.python.sql.handler.OracleDataHandler"
	print >> f, ""
	f.close()

def appendFileContent(mSourceFile, mTargetFileOutputStream):
	fsrc = open(mSourceFile, "r")
	content = fsrc.read()
	fsrc.close()
	
	print >> mTargetFileOutputStream, "-- " + os.path.basename(mSourceFile)
	mTargetFileOutputStream.write(content)
	print >> mTargetFileOutputStream, ""

def sqlDeploy(mAppName, mUser, mSYSPassword, mURL, mSourceZip, mUTILSSourceZip, mTempDir, mBaseDir, mSqlHomeDir, mExecuteScript):
	print '\n xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx' + '\n'
	try:
		if(mUser != '' and mSourceZip != ''):
			mDBEXTSConfig = os.path.join(mTempDir, 'dbexts_SYS.ini')
			if(os.path.isfile(mDBEXTSConfig)):
				os.remove(mDBEXTSConfig)
			generateDBEXTSConfig('SYS AS SYSDBA', mSYSPassword, mURL, mDBEXTSConfig)
			
			mSqlTempScript = os.path.join(mTempDir, mAppName + '.sql')
			if(os.path.isfile(mSqlTempScript)):
				os.remove(mSqlTempScript)
			
			
			mSqlTempDir = os.path.join(mTempDir , mAppName)
			mUTILSSqlTempDir = ''
			unzipFile(mSourceZip, mSqlTempDir)
			if (mUTILSSourceZip != ''):
				mUTILSSqlTempDir = os.path.join(mTempDir, mAppName + '_VIRTUAL7UTIL')
				unzipFile(mUTILSSourceZip, mUTILSSqlTempDir)
			

			mCreateSchema = false
			mCreateTables = false
			mUTILSCreateTables = false
			mUpdateTables = false
			mUTILSUpdateTables = false
			mUpdateVersions = []
			mUTILSUpdateVersions = []
			
			
			d = dbexts.dbexts(dbname='SYS AS SYSDBA', cfg=mDBEXTSConfig)
			d.raw("select count(*) from dba_users where upper(username) = upper('" + mUser + "')")
			if (int(d.results[0][0]) == 0) :
				mCreateSchema = true
				mCreateTables = true
				if(mUTILSSourceZip != ''):
					mUTILSCreateTables = true 
			
			if (not mCreateSchema) :
				existsConfigTable = false
				existsUTILSConfigTable = false
				dbVersion = None
				dbUTILSVersion = None
								
				d.raw("select count(*) from dba_tables where UPPER(OWNER)=UPPER('" + mUser + "') and UPPER(TABLE_NAME)=UPPER('CONFIG')")
				existsConfigTable = int(d.results[0][0]) != 0
				d.raw("select count(*) from dba_tables where UPPER(OWNER)=UPPER('" + mUser + "') and UPPER(TABLE_NAME)=UPPER('UTILS_CONFIG')")
				existsUTILSConfigTable = int(d.results[0][0]) != 0
				
				if(existsConfigTable):
					d.raw("select config_value from " + mUser + ".CONFIG where upper(config_key) = upper('SCHEMA_VERSION')")
					dbVersion = d.results[0][0]
				if(existsUTILSConfigTable):
					d.raw("select config_value from " + mUser + ".UTILS_CONFIG where upper(config_key) = upper('SCHEMA_VERSION')")
					if(mUTILSSourceZip == ''):
						dbVersion = d.results[0][0]
					else:
						dbUTILSVersion = d.results[0][0]

				
				if (dbVersion == None) :
						mCreateTables = true
				else :
					mUpdateVersions = getUpdateVersions(dbVersion, os.path.join(mSqlTempDir, 'updates'))
					if (mUpdateVersions) :
						mUpdateTables = true

				if(mUTILSSqlTempDir != ''):
					if (dbUTILSVersion == None) :
						mUTILSCreateTables = true
					else :
						mUTILSUpdateVersions = getUpdateVersions(dbUTILSVersion, os.path.join(mUTILSSqlTempDir, 'updates'))
						if (mUTILSUpdateVersions) :
							mUTILSUpdateTables = true
						
			# append to the sql script
			f = open(mSqlTempScript, 'a')
			
			if(mCreateSchema or mCreateTables):
				appendFileContent(os.path.join(mBaseDir, 'CREATE_' + mAppName + '.sql'), f)
			
			if (mCreateSchema) :
				print >> f, "ALTER SESSION SET CURRENT_SCHEMA = SYS"
				print >> f, "@" + os.path.join(os.path.join(mSqlTempDir, "create"), "000_run_as_sys_user.sql") + ";"
			
			if (mUTILSCreateTables) :
				appendFileContent(os.path.join(mBaseDir, 'CREATE_' + mAppName + '_VIRTUAL7UTIL.sql'), f)
				print >> f, "ALTER SESSION SET CURRENT_SCHEMA = " + mUser + ";"
				print >> f, "@" + os.path.join(os.path.join(mUTILSSqlTempDir, "create"), "000_run_as_app_user.sql") + ";"

			if (mCreateTables) :
				print >> f, "ALTER SESSION SET CURRENT_SCHEMA = " + mUser + ";"
				print >> f, "@" + os.path.join(os.path.join(mSqlTempDir, "create"), "000_run_as_app_user.sql") + ";"

			if (mUTILSUpdateTables) :
				print >> f, "ALTER SESSION SET CURRENT_SCHEMA = " + mUser + ";"
				for version in mUTILSUpdateVersions :
					appendFileContent(os.path.join(mBaseDir, "UPDATE_" + mAppName + "_VIRTUAL7UTIL_" + version + ".sql"), f)
					print >> f, "@" + os.path.join(os.path.join(mUTILSSqlTempDir, "updates"), "update_" + version + ".sql") + ";"

			if (mUpdateTables) :
				print >> f, "ALTER SESSION SET CURRENT_SCHEMA = " + mUser + ";"
				for version in mUpdateVersions :
					appendFileContent(os.path.join(mBaseDir, "UPDATE_" + mAppName + "_" + version + ".sql"), f)
					print >> f, "@" + os.path.join(os.path.join(mSqlTempDir, "updates"), "update_" + version + ".sql") + ";"
		
			print >> f, ""
			print >> f, "EXIT"
			print >> f, ""
			
			f.close()
		print '\n ### ### ###: SQL Deploy \n'
		
		runSqlScript(mURL, mSYSPassword, mSqlHomeDir, mSqlTempScript, mTempDir, mExecuteScript)
	except Exception, e:
		print e

def runSqlScript(mURL, mSYSPassword, mSqlHomeDir, mSqlTempScript, mTempDir, mExecuteScript) :
	try :
		sqlplusCmd = constructSqlPlusCmd(mURL, mSYSPassword)
		filePath = ''
		# TODO find a more elegant OS detection
		if os.getenv('os') == 'Windows_NT' :
			filePath = os.path.join(mTempDir, 'executSQL.bat')
			f = open(filePath, 'w+')
			print >> f, 'SET ORACLE_HOME=' + mSqlHomeDir
			print >> f, sqlplusCmd + ' @' + mSqlTempScript
			f.close()
			if(mExecuteScript == 'true'):
				executeExternalApplication([filePath])
		else :
			if(mExecuteScript == 'true'):
				executeExternalApplication([str(os.path.join(mSqlHomeDir, 'sqlplus')), 'sys/' + mSYSPassword + '@XE as sysdba', '@' + mSqlTempScript])

	except Exception, e :
		print(e)
    
def constructSqlPlusCmd(mURL, mSYSPassword) :
	try :
		URLParts = mURL.split(':')
		URLParts[3] = URLParts[3][1:]
		return "sqlplus -S SYS/" + mSYSPassword + " AS SYSDBA@'(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=" + URLParts[3] + ")(PORT=" + URLParts[4] + ")))(CONNECT_DATA=(SID=" + URLParts[5] + ")))'"
	except Exception, e :
		print(e)

def executeExternalApplication(command) :
	try :
		pb = ProcessBuilder(command)
		pb.redirectErrorStream(true)
		process = pb.start()
		inputStream = process.getInputStream()
		bufferedReader = BufferedReader(InputStreamReader(inputStream))
		s = bufferedReader.readLine()
		while s != None :
			print(s)
			s = bufferedReader.readLine()
		if (bufferedReader != None) :
			bufferedReader.close()
	except Exception, e :
		print(e)
		if (bufferedReader != None) :
			bufferedReader.close()
