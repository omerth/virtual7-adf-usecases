package com.virtual7.programaticPivotTableBinding.view.managed.testdyn;


import com.virtual7.util.view.ADFUtils;
import com.virtual7.util.view.JSFUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.List;

import javax.faces.event.ActionEvent;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.dvt.binding.BindingConstants;
import oracle.adf.view.faces.bi.component.pivotTable.UIPivotTable;
import oracle.adf.view.faces.bi.model.DataModel;

import oracle.adfinternal.view.faces.dvt.model.binding.FacesBindingFactory;
import oracle.adfinternal.view.faces.dvt.model.binding.pivotTable.FacesPivotTableBinding;
import oracle.adfinternal.view.faces.dvt.model.binding.pivotTable.FacesPivotTableBindingDef;

import oracle.jbo.common.JboXMLUtil;
import oracle.jbo.mom.xml.DefElementImpl;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLParseException;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;


public class TestDynMB {

    private static final String ITERATOR_BINDING_NAME = "EmployeesView1Iterator";
    private static final String OLD_PIVOT_BINDING_ID = "EmployeesView1";

    private static final String NEW_PIVOT_BINDING_ID = "NewPivotTableBinding";
    private static final String NEW_PIVOT_BINDING_DEF_XML =
        "<" + BindingConstants.PIVOT_TABLE_BINDING + " IterBinding=\"" + ITERATOR_BINDING_NAME + "\" " +
        BindingConstants.ATTR_ID + "=\"" + NEW_PIVOT_BINDING_ID + " \" xmlns=\"http://xmlns.oracle.com/adfm/dvt\">\n" +
        "      <" + BindingConstants.PIVOT_TABLE_DATA_MAP + ">\n" +
        "        <" + BindingConstants.PIVOT_TABLE_COLUMNS + ">\n" +
        "          <" + BindingConstants.BINDING_DATA + " " + BindingConstants.ATTR_AGGREGATE_DUPLICATES +
        "=\"true\" " + BindingConstants.ATTR_DEFAULT_AGGREGATE_TYPE + "=\"" + BindingConstants.TOTAL_TYPE_SUM +
        "\">\n" +
        "            <" + BindingConstants.BINDING_ITEM + " " + BindingConstants.ATTR_VALUE + "=\"Salary\"/>\n" +
        "          </" + BindingConstants.BINDING_DATA + ">\n" +
        "        </" + BindingConstants.PIVOT_TABLE_COLUMNS + ">\n" +
        "        <" + BindingConstants.PIVOT_TABLE_ROWS + ">\n" +
        "            <" + BindingConstants.BINDING_ITEM + " " + BindingConstants.ATTR_VALUE + "=\"DepartmentId\"/>\n" +
        "            <" + BindingConstants.BINDING_ITEM + " " + BindingConstants.ATTR_VALUE + "=\"LastName\"/>\n" +
        "        </" + BindingConstants.PIVOT_TABLE_ROWS + ">\n" +
        "        <" + BindingConstants.PIVOT_TABLE_PAGES + "/>\n" +
        "      </" + BindingConstants.PIVOT_TABLE_DATA_MAP + ">\n" +
        "</" + BindingConstants.PIVOT_TABLE_BINDING + ">";

    private UIPivotTable pivotTable;

    public TestDynMB() {
        super();
    }

    public void setPivotTable(UIPivotTable pivotTable) {
        this.pivotTable = pivotTable;
    }

    public UIPivotTable getPivotTable() {
        return pivotTable;
    }

    /**
     * Action called on click of the button to create new binding.
     *
     * @param actionEvent the action event.
     */
    public void onClickNewBinding(ActionEvent actionEvent) {
        // Get the binding container for the current page.
        DCBindingContainer bindingContainer = ADFUtils.getDCBindingContainer();

        // Print all bindings.
        printAllBindings(bindingContainer);

        // Get the defined pivot table binding from the pagedef.
        FacesPivotTableBinding origPTBinding = getPivotTableBinding(OLD_PIVOT_BINDING_ID);
        FacesPivotTableBindingDef origPTDef = null;
        if (origPTBinding != null) {
            origPTDef = (FacesPivotTableBindingDef)origPTBinding.getDef();
        }

        // Get the new defined pivot table binding.
        FacesPivotTableBinding newPTBinding = getPivotTableBinding(NEW_PIVOT_BINDING_ID);
        if (newPTBinding == null) {
            // Binding not yet defined so create new binding.
            newPTBinding = createNewPTBinding(bindingContainer, NEW_PIVOT_BINDING_ID, NEW_PIVOT_BINDING_DEF_XML);
        }

        // Get the definition object of the pivot table.
        FacesPivotTableBindingDef newPTDef = null;
        if (newPTBinding != null) {
            newPTDef = (FacesPivotTableBindingDef)newPTBinding.getDef();
        }

        // Set the value of the pivot table to the new binding.
        if (newPTBinding != null) {
            DataModel dataModel = newPTBinding.getDataModel();
            Object dataModelFromBindings =
                JSFUtils.resolveExpression("#{bindings." + NEW_PIVOT_BINDING_ID + ".pivotTableModel}");
            this.getPivotTable().setValue(dataModel);
        }
    }

    /**
     * Action called on click of the button to create new binding.
     *
     * @param actionEvent the action event.
     */
    public void onClickOldBinding(ActionEvent actionEvent) {
        Object dataModelFromBindings =
            JSFUtils.resolveExpression("#{bindings." + OLD_PIVOT_BINDING_ID + ".pivotTableModel}");
        this.getPivotTable().setValue(dataModelFromBindings);
    }


    /**
     * Print all the bindings defined in the given binding container.
     *
     * @param bindingContainer the binding container.
     */
    private void printAllBindings(DCBindingContainer bindingContainer) {
        List ctrlBindings = bindingContainer.getControlBindings();
        for (Object o : ctrlBindings) {
            Object t = o;
            System.out.println("ctrlBinding:" + o);
        }

        List attrBindings = bindingContainer.getAttributeBindings();
        for (Object o : attrBindings) {
            Object t = o;
            System.out.println("attrBinding:" + o);
        }

        List opBindings = bindingContainer.getOperationBindings();
        for (Object o : opBindings) {
            Object t = o;
            System.out.println("opBinding:" + o);
        }
    }

    /**
     * Get a pivot table binding.
     *
     * @param id the id of the pivot table binding.
     * @return a pivot table binding object or null.
     */
    private FacesPivotTableBinding getPivotTableBinding(String id) {
        FacesPivotTableBinding binding = null;

        Object bindingObj = JSFUtils.resolveExpression("#{bindings." + id + "}");
        if (FacesPivotTableBinding.class.isInstance(bindingObj)) {
            binding = (FacesPivotTableBinding)bindingObj;
        }

        return binding;
    }

    /**
     * Create a new pivot table binding and add it to the binding container.
     *
     * @param bindingContainer the binding container.
     * @param pivotDefXML the pivot table definition xml.
     * @return the new pivot table binding created or null if it can not be created.
     */
    private FacesPivotTableBinding createNewPTBinding(DCBindingContainer bindingContainer, String newPTBindingId,
                                                      String pivotDefXML) {
        FacesPivotTableBinding binding = null;

        DOMParser parser = JboXMLUtil.createDOMParser(false);
        ByteArrayInputStream inStream = null;
        try {
            // Parse the XML and get the first pivot table binding element.
            inStream = new ByteArrayInputStream(pivotDefXML.getBytes());
            parser.parse(inStream);

            XMLDocument document = parser.getDocument();
            NodeList nodes = document.getElementsByTagName(BindingConstants.PIVOT_TABLE_BINDING);
            if (nodes != null && nodes.getLength() > 0) {
                // Get the first pivot table binding xml element.
                Node item = nodes.item(0);

                // Construct the definition element which described the pivot table.
                DefElementImpl xmlDef = constructDefElem(item);

                // Construct the pivot table binding definition object and load the description from the xml definition.
                FacesBindingFactory fact1 = new FacesBindingFactory();
                FacesPivotTableBindingDef ptDef =
                    (FacesPivotTableBindingDef)fact1.createControlDef(BindingConstants.PIVOT_TABLE_BINDING);
                ptDef.loadFromXML(xmlDef);

                // Construct the binding object and add it to the binding container.
                binding = (FacesPivotTableBinding)ptDef.createControlBinding(bindingContainer);
                binding.setName(newPTBindingId);
                System.out.println("Created new pivot tale binding with name:" + binding.getName());
            }
        } catch (XMLParseException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }

        return binding;
    }


    /**
     * Creates a <code>DefElement</code> from an XML <code>Node</code>.
     */

    /**
     * Create a def element from an xml node.
     * @param xmlNode
     * @return
     * @throws Exception
     */
    private DefElementImpl constructDefElem(Node xmlNode) throws Exception {
        DefElementImpl defEl = null;

        if (xmlNode != null) {
            // Construct new def element with same name.
            defEl = new DefElementImpl(xmlNode.getNodeName());

            // Get the attributes of the current node and copy them to the def elem.
            NamedNodeMap attrs = xmlNode.getAttributes();
            if (attrs != null && attrs.getLength() > 0) {
                int nrAttrs = attrs.getLength();
                for (int i = 0; i < nrAttrs; i++) {
                    Node curAttr = attrs.item(i);
                    String namespace = curAttr.getNamespaceURI();
                    if (namespace != null && !"".equals(namespace)) {
                        defEl.setAttributeNS(namespace, curAttr.getNodeName(), curAttr.getNodeValue());
                    } else {
                        defEl.setAttribute(curAttr.getNodeName(), curAttr.getNodeValue());
                    }
                }
            }

            // Copy recursively all child nodes.
            NodeList children = xmlNode.getChildNodes();
            if (children != null && children.getLength() > 0) {
                int nrChildren = children.getLength();
                for (int i = 0; i < nrChildren; i++) {
                    Node curChild = children.item(i);
                    DefElementImpl chldDef = constructDefElem(curChild);
                    if (chldDef != null) {
                        defEl.appendChild(chldDef);
                    }
                }
            }
        }

        return defEl;
    }

    /**
     * It if doesn't already exist in the current binding container, create a FacesCtrlActionBinding
     * for the data control method whose information is passed in.
     **/
    //    private void ensureFacesCtrlActionToCallDataControlMethod(String actionBindingName, String dataControlName,
    //                                                              String methodName, String[][] argNameTypeValues,
    //                                                              DCBindingContainer container) {
    //        DCBindingContainer bc;
    //        if (container == null) {
    //            HttpServletRequest request = (HttpServletRequest)ADFContext.getCurrent().getEnvironment().getRequest();
    //            bc = (DCBindingContainer)request.getAttribute("bindings");
    //
    //        } else
    //            bc = container;
    //
    //        if (bc.getControlBinding(actionBindingName) == null) {
    //            FacesCtrlActionDef def = new FacesCtrlActionDef();
    //            HashMap initVals = new HashMap();
    //            initVals.put(JUCtrlActionDef.PNAME_ActionID, "999");
    //            initVals.put(JUCtrlActionDef.PNAME_DataControl, dataControlName);
    //            initVals.put(JUCtrlActionDef.PNAME_InstanceName, dataControlName + ".dataProvider");
    //            initVals.put(JUCtrlActionDef.PNAME_ReturnName,
    //                         dataControlName + ".methodResults." + dataControlName + "_dataProvider_" + methodName +
    //                         "_result");
    //            initVals.put(JUCtrlActionDef.PNAME_IsViewObjectMethod, Boolean.FALSE);
    //            initVals.put(JUCtrlActionDef.PNAME_RequiresUpdateModel, Boolean.TRUE);
    //            initVals.put(JUCtrlActionDef.PNAME_IsLocalObjectReference, Boolean.FALSE);
    //            initVals.put(JUCtrlActionDef.PNAME_MethodName, methodName);
    //            // support input parameters for the action binding
    //            // input parameters are defined through the argNameTypeValues input parameter that is an Array of Array,
    //            //  with the second Array containing three strings: the name, Java type and value (EL expression that gets hold of the value) for the input parameter
    //            int numArgDefs = argNameTypeValues == null ? 0 : argNameTypeValues.length;
    //            if (numArgDefs > 0) {
    //                DCMethodParameterDef[] argDefs = new DCMethodParameterDef[numArgDefs];
    //                for (int z = 0; z < numArgDefs; z++) {
    //                    String[] argNameTypeValue = (String[])argNameTypeValues[z];
    //                    if (argNameTypeValue == null || argNameTypeValue.length != 3) {
    //                        throw new RuntimeException("Expecting argDef as String[3] of {name,type,value}");
    //                    }
    //                    argDefs[z] =
    //                            new DCMethodParameterDef(argNameTypeValue[0], argNameTypeValue[1], argNameTypeValue[2]);
    //                }
    //                initVals.put(JUCtrlActionDef.PNAME_Arguments, argDefs);
    //            }
    //            def.init(initVals);
    //            // at this point we can add the actionBinding to the binding container
    //            FacesCtrlActionBinding b = (FacesCtrlActionBinding)def.createControlBinding(bc);
    //            // finally set the name under which we want to be able to find this binding in the container
    //            b.setName(actionBindingName);
    //        }
    //    }

}
