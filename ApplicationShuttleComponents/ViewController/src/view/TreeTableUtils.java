package view;

import com.virtual7.util.view.ADFUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.data.RichTree;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;


public class TreeTableUtils {

//    private String searchString = "";
    private String searchType = "CONTAIN";
//    private List searchAttributes = new ArrayList();
//    private RichTreeTable tree1 = null;
      static ADFLogger logger = ADFLogger.createADFLogger(TreeTableUtils.class);    

    public TreeTableUtils() {
        super();
    }

    public static void searchTreeTable(RichTreeTable tree, String searchString, String searchType, List searchAttributes) {
        JUCtrlHierBinding treeBinding = null;

        //get handle to tree if it does not exist. If tree component cannot be
        //found in view, exit this function
//        if (tree1 == null) {
//            this.findTreeInView();
//            if (tree1 == null) {
//                //tree not found
//                log("The tree component could not be found in the view. Please check for naming containers. Search function cancelled");
//                return;
//            }
//        }
        //Get the JUCtrlHierbinding reference from the PageDef
        CollectionModel model = (CollectionModel)tree.getValue();
        treeBinding = (JUCtrlHierBinding)model.getWrappedData();

        //Read the attributes to search in from the SelectManyChoice component
        String searchAttributeArray[] =
            (String[])searchAttributes.toArray(new String[searchAttributes.size()]);

        //Define a node to search in. In this example, the root node is used
        JUCtrlHierNodeBinding root = treeBinding.getRootNodeBinding();

        //However, if the user used the "Show as Top" context menu option to
        //shorten the tree display, then we only search starting from this top
        //mode

//        List topNode = (List)tree.getFocusRowKey();
//        if (topNode != null) {
//            //make top node the root node for the search
//            root = treeBinding.findNodeByKeyPath(topNode);
//        }

        //Select the tree items that match the search criteria and expand the
        //tree to display them
        RowKeySet resultRowKeySet =
            searchTreeNode(root, searchAttributeArray, searchType,
                           searchString);
        RowKeySet disclosedRowKeySet =
            buildDiscloseRowKeySet(treeBinding, resultRowKeySet);
        tree.setSelectedRowKeys(resultRowKeySet);
        tree.setDisclosedRowKeys(disclosedRowKeySet);

        //AdfFacesContext.getCurrentInstance().addPartialTarget(tree1);
        ADFUtils.addPartialTarget(tree);
    }

    /**
     * Method that parses an ADF bound ADF Faces tree component to find search string matches
     * in one of the specified attribute names. Attribute names are ignored if they don't exist
     * in the search node. The method performs a recursiv search and returns a RowKeySet with the
     * row keys of all nodes that contain the search string
     * @param  node The JUCtrlHierNodeBinding instance to search
     * @param  searchAttributes An array of attribute names to search in
     * @param  searchType defines where the search is started within the text. Valid values are
     *         START, CONTAIN, END. If NULL the "CONTAIN" is set as the default
     * @param  searchString  The search condition
     * @return RowKeySet row keys
     */
    private static RowKeySet searchTreeNode(JUCtrlHierNodeBinding node,
                                     String[] searchAttributes,
                                     String searchType, String searchString) {
        RowKeySetImpl rowKeys = new RowKeySetImpl();
        //set default search
        String _searchType =
            searchType == null ? "CONTAIN" : searchType.length() > 0 ?
                                             searchType : "CONTAIN";

        //Sanity checks
        if (node == null) {
            logger.log("Node passed as NULL");
            return rowKeys;
        }
        if (searchAttributes == null || searchAttributes.length < 1) {
            logger.log(node.getName() +
                ": search attribute is NULL or has a ZERO length");
            return rowKeys;
        }
        if (searchString == null || searchString.length() < 1) {
            logger.log(node.getName() + ": Search string cannot be NULL or EMPTY");
            return rowKeys;
        }

        Row nodeRow = node.getRow();
        if (nodeRow != null) {
            for (int i = 0; i < searchAttributes.length; i++) {
                String compareString = "";
                try {
                    Object attribute =
                        nodeRow.getAttribute(searchAttributes[i]);
                    if (attribute instanceof String) {
                        compareString = (String)attribute;
                    } else {
                        //try the toString method as a simple fallback
                        compareString = attribute.toString();
                    }
                } catch (oracle.jbo.JboException attributeNotFound) {
                    //node does not have attribute. Exclude from search
                }
                //compare strings case insesitive.
                if (_searchType.equalsIgnoreCase("CONTAIN") &&
                    compareString.toUpperCase().indexOf(searchString.toUpperCase()) >
                    -1) {
                    //get row key
                    rowKeys.add(node.getKeyPath());
                } else if (_searchType.equalsIgnoreCase("START") &&
                           compareString.toUpperCase().startsWith(searchString.toUpperCase())) {
                    //get row key
                    rowKeys.add(node.getKeyPath());
                } else if (_searchType.equalsIgnoreCase("END") &&
                           compareString.toUpperCase().endsWith(searchString.toUpperCase())) {
                    //get row key
                    rowKeys.add(node.getKeyPath());
                }
            }
        }

        List<JUCtrlHierNodeBinding> children = node.getChildren();

        if (children != null) {
            for (JUCtrlHierNodeBinding _node : children) {
                //Each child search returns a row key set that must be added to the
                //row key set returned by the overall search
                RowKeySet rks =
                    searchTreeNode(_node, searchAttributes, searchType,
                                   searchString);
                if (rks != null && rks.size() > 0) {
                    rowKeys.addAll(rks);
                }
            }
        }
        return rowKeys;
    }

    /**
     * Helper method that returns a list of parent node for the RowKeySet passed
     * as the keys argument. The RowKeySet can be used to disclose the folders in
     * which the keys reside. Node that to disclose a full branch, all RowKeySet
     * that are in the path must be defined
     *
     * @param  treeBinding ADF tree binding instance read from the PageDef file
     * @param  keys  RowKeySet containing List entries of oracle.jbo.Key
     * @return RowKeySet of parent keys to disclose
     */
    private static RowKeySet buildDiscloseRowKeySet(JUCtrlHierBinding treeBinding,
                                             RowKeySet keys) {
        RowKeySetImpl discloseRowKeySet = new RowKeySetImpl();
        Iterator iter = keys.iterator();
        while (iter.hasNext()) {
            List keyPath = (List)iter.next();
            JUCtrlHierNodeBinding node =
                treeBinding.findNodeByKeyPath(keyPath);
            if (node != null && node.getParent() != null &&
                !node.getParent().getKeyPath().isEmpty()) {
                //store the parent path
                discloseRowKeySet.add(node.getParent().getKeyPath());

                //call method recursively until no parents are found
                RowKeySetImpl parentKeySet = new RowKeySetImpl();
                parentKeySet.add(node.getParent().getKeyPath());
                RowKeySet rks =
                    buildDiscloseRowKeySet(treeBinding, parentKeySet);
                discloseRowKeySet.addAll(rks);
            }
        }
        return discloseRowKeySet;
    }

//    private static void log(String message) {
//        logger.log(ADFLogger.WARNING, message);
//    }

    //To learn what the code below is doing, please see Sample #58 on ADF Code Corner
    //http://www.oracle.com/technetwork/developer-tools/adf/learnmore/index-101235.html

//    private void findTreeInView() {
//        FacesContext fctx = FacesContext.getCurrentInstance();
//        UIViewRoot root = fctx.getViewRoot();
//        //hard coding tree component Id with its surrounding naming container ID
//        //PanelCollection
//        root.invokeOnComponent(fctx, "pc1:tree1", new ContextCallback() {
//                public void invokeContextCallback(FacesContext facesContext,
//                                                  UIComponent uiComponent) {
//                    tree1 = (RichTreeTable)uiComponent;
//                }
//            });
//    }
}
