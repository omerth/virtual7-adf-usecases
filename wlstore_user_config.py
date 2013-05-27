import time

print '\n Storing started at ' + time.asctime() + '\n'

try: 

    mUserConfigFile = sys.argv[1]
    mUserKeyFile = sys.argv[2]
    mUserName = sys.argv[3]
    mPassword = sys.argv[4]
    mAdminURL = sys.argv[5]
    
    connect(username=mUserName, password=mPassword, url=mAdminURL)
    
    storeUserConfig(mUserConfigFile, mUserKeyFile)
    
    print '#########################################################'
    print '#####     Stored                                #########'
    print '#########################################################'
    exit()

except Exception, ex:
    print ex.getMessage()
    print '#########################################################'
    print '#####     Failed                                #########'
    print '#####     Contact support with Exception Stack  #########'
    print '#########################################################'
    exit()
