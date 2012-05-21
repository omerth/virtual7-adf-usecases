package com.virtual7.bcCachingService.model.server.serviceinterface;

import com.virtual7.bcCachingService.model.common.serviceinterface.AppModuleService;
import com.virtual7.bcCachingService.model.views.common.DepartmentServiceVOSDO;

import java.lang.reflect.Method;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.interceptor.Interceptors;

import oracle.jbo.common.Diagnostic;
import oracle.jbo.common.sdo.SDOHelper;
import oracle.jbo.common.service.types.FindControl;
import oracle.jbo.common.service.types.FindCriteria;
import oracle.jbo.common.service.types.ProcessControl;
import oracle.jbo.server.svc.ServiceContextInterceptor;
import oracle.jbo.server.svc.ServiceImpl;
import oracle.jbo.service.errors.ServiceException;

import oracle.webservices.annotations.PortableWebService;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri May 18 15:44:16 EEST 2012
// ---------------------------------------------------------------------
@Stateless(name="com.virtual7.bcCachingService.model.common.AppModuleServiceBean", mappedName="AppModuleServiceBean")
@Remote(AppModuleService.class)
@PortableWebService(targetNamespace="/com/virtual7/bcCachingService/model/common/", serviceName="AppModuleService",
    portName="AppModuleServiceSoapHttpPort", endpointInterface="com.virtual7.bcCachingService.model.common.serviceinterface.AppModuleService")
@Interceptors( { ServiceContextInterceptor.class })
public class AppModuleServiceImpl extends ServiceImpl implements AppModuleService {
    private static boolean _isInited = false;

    /**
     * This is the default constructor (do not remove).
     */
    public AppModuleServiceImpl() {
        init();
        setApplicationModuleDefName("com.virtual7.bcCachingService.model.AppModule");
        setConfigurationName("AppModuleService");
    }

    /**
     * Generated method. Do not modify. Do initialization in the constructor
     */
    protected void init() {
        if (_isInited) {
            return;
        }
        synchronized (AppModuleServiceImpl.class) {
            if (_isInited) {
                return;
            }
            try {
                SDOHelper.INSTANCE.defineSchema("com/virtual7/bcCachingService/model/common/serviceinterface/", "AppModuleService.xsd");
                _isInited = true;
            } catch (Exception ex) {
                Diagnostic.printStackTrace(ex);
            }
        }
    }

    /**
     * createDepartmentServiceVO: generated method. Do not modify.
     */
    public DepartmentServiceVOSDO createDepartmentServiceVO(DepartmentServiceVOSDO departmentServiceVO) throws ServiceException {
        return (DepartmentServiceVOSDO)create(departmentServiceVO, "DepartmentServiceVO");
    }

    /**
     * updateDepartmentServiceVO: generated method. Do not modify.
     */
    public DepartmentServiceVOSDO updateDepartmentServiceVO(DepartmentServiceVOSDO departmentServiceVO) throws ServiceException {
        return (DepartmentServiceVOSDO)update(departmentServiceVO, "DepartmentServiceVO");
    }

    /**
     * deleteDepartmentServiceVO: generated method. Do not modify.
     */
    public void deleteDepartmentServiceVO(DepartmentServiceVOSDO departmentServiceVO) throws ServiceException {
        delete(departmentServiceVO, "DepartmentServiceVO");
    }

    /**
     * mergeDepartmentServiceVO: generated method. Do not modify.
     */
    public DepartmentServiceVOSDO mergeDepartmentServiceVO(DepartmentServiceVOSDO departmentServiceVO) throws ServiceException {
        return (DepartmentServiceVOSDO)merge(departmentServiceVO, "DepartmentServiceVO");
    }

    /**
     * findDepartmentServiceVO: generated method. Do not modify.
     */
    public List<DepartmentServiceVOSDO> findDepartmentServiceVO(FindCriteria findCriteria,
                                                                FindControl findControl) throws ServiceException {
        return (List<DepartmentServiceVOSDO>)find(findCriteria, findControl, "DepartmentServiceVO",
                                                  DepartmentServiceVOSDO.class);
    }

    /**
     * processDepartmentServiceVO: generated method. Do not modify.
     */
    public List<DepartmentServiceVOSDO> processDepartmentServiceVO(String changeOperation,
                                                                   List<DepartmentServiceVOSDO> departmentServiceVO,
                                                                   ProcessControl processControl) throws ServiceException {
        return (List<DepartmentServiceVOSDO>)process(changeOperation, processControl, departmentServiceVO,
                                                     "DepartmentServiceVO");
    }
}