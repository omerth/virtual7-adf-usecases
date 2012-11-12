package com.virtual7.programaticPivotTableBinding.view.managed.ajustable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import oracle.adfinternal.view.faces.bi.renderkit.model.TestBdaUtils;

import oracle.dss.util.ColumnOutOfRangeException;
import oracle.dss.util.CubeDataAccess;
import oracle.dss.util.CubeDataDirector;
import oracle.dss.util.DataAccessAdapter;
import oracle.dss.util.DataAvailableEvent;
import oracle.dss.util.DataDirector;
import oracle.dss.util.DataDirectorListener;
import oracle.dss.util.DataMap;
import oracle.dss.util.DataSource;
import oracle.dss.util.EdgeOutOfRangeException;
import oracle.dss.util.LayerMetadataMap;
import oracle.dss.util.LayerOutOfRangeException;
import oracle.dss.util.MetadataMap;
import oracle.dss.util.QDR;
import oracle.dss.util.QDRInterface;
import oracle.dss.util.RelationalDataAccess;
import oracle.dss.util.RelationalDataDirector;
import oracle.dss.util.RowOutOfRangeException;
import oracle.dss.util.SliceOutOfRangeException;

public class FakeDataSource extends DataAccessAdapter implements CubeDataDirector, CubeDataAccess,
                                                                 RelationalDataDirector, RelationalDataAccess,
                                                                 DataSource { // LocalDataSource

    // *************   PARAMS (Change at will for testing)   *******************

    // The extent of the column and row edges, respectively:
    // Note:  Internal code should never access this ivar directly.  _getEdgeExtent should
    // be used instead, since that method overrides the ivar when the layerCount is 0.
    private int[] edgeExtent = { 54, 75 };

    // TEST: make one or both of these 0.
    // The number of layers in the column and row edges, respectively:
    private int[] layerCount = { 3, 2 };

    // The number of children each member has--e.g. if the branching factor
    // is 2, the members in the innermost layer have extent 1, the members
    // in the next layer have extent 2 (thus 2 children), the members in the
    // next layer have extent 4 (thus 2 children since each child has extent
    // 2), and so on.  The numbers are for the column and row edges, respectively.
    private int[] branchingFactor = { 3, 5 };

    // The getter is in the DataAccess methods section

    public void setEdgeExtent(int edge, int extent) {
        if (extent < 0)
            extent = 0;
        this.edgeExtent[edge] = extent;
        m_updatedValues.clear(); // they're basically getting a new pivot table, so clear previous updates
    }

    // The getter is in the DataAccess methods section

    public void setLayerCount(int edge, int layerCount) {
        if (layerCount < 0)
            layerCount = 0;
        this.layerCount[edge] = layerCount;
        m_updatedValues.clear(); // they're basically getting a new pivot table, so clear previous updates
    }

    public int getBranchingFactor(int edge) {
        return branchingFactor[edge];
    }

    public void setBranchingFactor(int edge, int branchingFactor) {
        if (branchingFactor <= 0)
            branchingFactor = 1;
        this.branchingFactor[edge] = branchingFactor;
        m_updatedValues.clear(); // they're basically getting a new pivot table, so clear previous updates
    }

    // METHODS

    public FakeDataSource() {
        System.out.println("fds created");
    }


    // Other constructors will allow custom local data cubes to be set up,
    // and other methods will allow them to be filled with data more efficiently

    public DataDirector createDataDirector() {
        return this;
    }

    public CubeDataDirector createCubeDataDirector() {
        return this;
    }

    public RelationalDataDirector createRelationalDataDirector() {
        return this;
    }

    public void addDataDirectorListener(DataDirectorListener l) {
        listener = l;
        l.viewDataAvailable(new DataAvailableEvent(this, this));
    }

    public void removeDataDirectorListener(DataDirectorListener l) {
    }

    public Object clone() {
        return new FakeDataSource();
    }


    public DataMap getDataMap() {
        return new DataMap("");
    }

    public MetadataMap getMetadataMap(int edge, int layer) throws EdgeOutOfRangeException, LayerOutOfRangeException {
        return new MetadataMap((String)null);
    }

    // *******************   HELPER METHODS   **********************************

    private void checkEdge(int edge) throws EdgeOutOfRangeException {
        if (edge < 0 || edge > 1)
            throw new EdgeOutOfRangeException(edge, 1);
    }

    private void checkLayer(int edge, int layer) throws LayerOutOfRangeException {
        if (layer < 0 || layer >= layerCount[edge])
            throw new LayerOutOfRangeException(layer, layerCount[edge] - 1);
    }

    private void checkSlice(int edge, int slice) throws SliceOutOfRangeException {
        if (slice < 0 || slice >= _getEdgeExtent(edge))
            throw new SliceOutOfRangeException(slice, _getEdgeExtent(edge) - 1);
    }

    private void checkRowColumn(int row, int column) throws RowOutOfRangeException, ColumnOutOfRangeException {
        if (row < 0 || row >= _getEdgeExtent(DataDirector.ROW_EDGE))
            throw new RowOutOfRangeException(row, _getEdgeExtent(DataDirector.ROW_EDGE) - 1);
        if (column < 0 || column >= _getEdgeExtent(DataDirector.COLUMN_EDGE))
            throw new ColumnOutOfRangeException(column, _getEdgeExtent(DataDirector.COLUMN_EDGE) - 1);
    }


    // ********   DATAACCESS1/2/3 METHODS THAT ARE IMPLEMENTED   ***************

    public int getEdgeExtent(int edge) throws EdgeOutOfRangeException {
        try {
            return _getEdgeExtent(edge);
        } catch (IndexOutOfBoundsException e) {
            throw new EdgeOutOfRangeException(edge, 1);
        }
    }

    // Unlike the main version, this version throws an *unchecked* (ArrayIndexOutOfBoundsException)
    // exception if the param is out of bounds.

    private int _getEdgeExtent(int edge) {
        // If the header is empty (has 0 layers), then the edge extent must be either 0 or 1, so we force it to 1 if it is above 1.  Otherwise the pivot table is invalid--not only is it logically wrong for an empty header to have multiple slices, but each of those slices has the same QDR (the empty map), causing them to evaluate as "equal" to each other, which causes problems.
        // This check lives here, in the getter, rather than in setLayerCount and setEdgeExtent, for 2 reasons:
        //   1) We probably can't control the order in which layerCount and edgeExtent are set by the demo page that uses this datasource, and if it set edgeExtent first, the edgeExtent could wind up being incorrectly set to 0.  Specifically, if the caller attempts to set edgeExtent to a value >1 before changing layerCount from 0 to a larger value, they will wind up with an edgeExtent of 0, which isn't what they wanted.
        //   2) For users of the demo, it's more convenient not to have to set the edgeExtent back to a reasonable value after temporarily setting the layerCount to 0.
        // TBD: If we move this check to the setters, then setLayerCount and setEdgeExtent must both say "if layerCt==0 && edgeExtent>1 then edgeExtent=1", where layerCt and edgeExtent are the new values for that edge in both cases.  We'd need to doc this behavior and caution that when setting both values, edgeExtent should be set 2nd to avoid the problem described in (1) above.  We'd have to find a way to force the demo page that uses this datasource to set them in the correct order.
        if (layerCount[edge] == 0 && edgeExtent[edge] > 1) {
            return 1;
        } else {
            return edgeExtent[edge];
        }
    }

    public Object getValue(int row, int col, String type) throws RowOutOfRangeException, ColumnOutOfRangeException {
        checkRowColumn(row, col);
        if (type != null && type.equals(DataMap.DATA_VALUE)) {
            String key = "r" + row + "c" + col;
            if (m_updatedValues.containsKey(key))
                return m_updatedValues.get(key);
        }
        return row + ", " + col;
    }

    public boolean setValue(Object data, int row, int col, String type) throws RowOutOfRangeException,
                                                                               ColumnOutOfRangeException {
        if (type != null && type.equals(DataMap.DATA_VALUE)) {
            String key = "r" + row + "c" + col;
            m_updatedValues.put(key, data);
            return true;
        }
        return false;
    }

    public int getLayerCount(int edge) throws EdgeOutOfRangeException {
        checkEdge(edge);
        return _getLayerCount(edge);
    }

    // Unlike the main version, this version throws an *unchecked* (ArrayIndexOutOfBoundsException)
    // exception if the param is out of bounds.

    private int _getLayerCount(int edge) {
        return layerCount[edge];
    }

    public int getSliceMemberCount(int edge, int slice) throws EdgeOutOfRangeException, SliceOutOfRangeException {
        checkEdge(edge);
        checkSlice(edge, slice);
        return layerCount[edge];
    }

    public int getSliceOutlineLayer(int edge, int slice) throws EdgeOutOfRangeException, SliceOutOfRangeException {
        checkEdge(edge);
        checkSlice(edge, slice);
        return 0;
    }

    public int getMemberDepth(int edge, int layer, int slice) throws EdgeOutOfRangeException, LayerOutOfRangeException,
                                                                     SliceOutOfRangeException {
        checkEdge(edge);
        checkLayer(edge, layer);
        checkSlice(edge, slice);
        return _getMemberDepth(edge, layer, slice);
    }

    // This version doesn't check whether the params are in-bounds.  The main version does that.

    private int _getMemberDepth(int edge, int layer, int slice) {
        return 1;
    }

    public int getMemberStartLayer(int edge, int layer, int slice) throws EdgeOutOfRangeException,
                                                                          LayerOutOfRangeException,
                                                                          SliceOutOfRangeException {
        checkEdge(edge);
        checkLayer(edge, layer);
        checkSlice(edge, slice);
        return _getMemberStartLayer(edge, layer, slice);
    }

    // This version doesn't check whether the params are in-bounds.  The main version does that.

    private int _getMemberStartLayer(int edge, int layer, int slice) {
        return layer;
    }

    public int getMemberLogicalLayer(int edge, int layer, int slice) throws EdgeOutOfRangeException,
                                                                            LayerOutOfRangeException,
                                                                            SliceOutOfRangeException {
        checkEdge(edge);
        checkLayer(edge, layer);
        checkSlice(edge, slice);
        return layer;
    }

    /**
     * Returns a String of the form "EdgeXLayerYY", e.g. "Edge1Layer02"
     */
    public Object getLayerMetadata(int edge, int layer, String type) throws EdgeOutOfRangeException,
                                                                            LayerOutOfRangeException {
        checkEdge(edge);
        checkLayer(edge, layer);
        return _getLayerMetadata(edge, layer, type);
    }

    // This version doesn't check whether the params are in-bounds.  The main version does that.
    // This version returns a String, not an Object.

    private String _getLayerMetadata(int edge, int layer, String type) {
        String leadingZero = (layer < 10) ? "0" : ""; // so layer names will sort nicely
        return "Edge" + edge + "Layer" + leadingZero + layer;
    }

    public int getMemberExtent(int edge, int layer, int slice) throws EdgeOutOfRangeException,
                                                                      LayerOutOfRangeException,
                                                                      SliceOutOfRangeException {
        checkEdge(edge);
        checkLayer(edge, layer);
        checkSlice(edge, slice);
        int extent = _getMemberExtent(edge, layer, slice);
        int startSlice = slice - TestBdaUtils.mathMod(slice, extent);
        int maxExtent = _getEdgeExtent(edge) - startSlice;
        return Math.min(extent, maxExtent);
    }

    // This version doesn't check whether the extent needs to be reduced because the slice is
    // near the end, or check whether the params are in-bounds.  The main version does that.

    private int _getMemberExtent(int edge, int layer, int slice) {
        return (int)Math.pow(branchingFactor[edge], layerCount[edge] - layer - 1);
    }

    public int getMemberStartSlice(int edge, int layer, int slice) throws EdgeOutOfRangeException,
                                                                          LayerOutOfRangeException,
                                                                          SliceOutOfRangeException {
        checkEdge(edge);
        checkLayer(edge, layer);
        checkSlice(edge, slice);
        return _getMemberStartSlice(edge, layer, slice);
    }

    // This version doesn't check whether the params are in-bounds.  The main version does that.

    private int _getMemberStartSlice(int edge, int layer, int slice) {
        return slice - TestBdaUtils.mathMod(slice, _getMemberExtent(edge, layer, slice));
    }

    public Object getMemberMetadata(int edge, int layer, int slice, String type) throws EdgeOutOfRangeException,
                                                                                        LayerOutOfRangeException,
                                                                                        SliceOutOfRangeException {
        if (type.equals(MetadataMap.METADATA_DRILLSTATE)) {
            return DataDirector.DRILLSTATE_NOT_DRILLABLE;
        }
        // Note that type (other than drillstate) is ignored for now.
        // No need to call checkEdge since Edge checked in the switch stmt.
        // No need to call checkLayer/Slice since they are checked in getMemberStartLayer/Slice.
        int startLayer = getMemberStartLayer(edge, layer, slice);
        int startSlice = getMemberStartSlice(edge, layer, slice);
        switch (edge) {
        case DataDirector.COLUMN_EDGE:
            return startLayer + ", " + startSlice;
        case DataDirector.ROW_EDGE:
            return startSlice + ", " + startLayer;
        default:
            throw new EdgeOutOfRangeException(edge, 1);
        }
    }

    // TODO: make both of these methods throw SOORE when slice is OOR.

    public boolean isFetched(int[] startSlice, int[] endSlice) throws SliceOutOfRangeException {
        return true;
    }

    public boolean forceFetch(int[] startSlice, int[] endSlice) throws SliceOutOfRangeException {
        return true;
    }

    public boolean isMemberExtentComplete(int edge, int layer, int slice) throws EdgeOutOfRangeException,
                                                                                 LayerOutOfRangeException,
                                                                                 SliceOutOfRangeException {
        checkEdge(edge);
        checkLayer(edge, layer);
        checkSlice(edge, slice);
        return true;
    }

    public int getEdgeCount() {
        return 2; // only support DataDirector.COLUMN_EDGE and DataDirector.ROW_EDGE
    }

    // ****************   UNIMPLEMENTED DATAACCESS METHODS   *******************

    public Object getSliceLabel(int edge, int slice, String type) throws EdgeOutOfRangeException,
                                                                         SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public int getEdgeCurrentSlice(int edge) throws EdgeOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public int[] getEdgeCurrentHPos(int edge) throws EdgeOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    /**
     * Per the DataAccess contract, this method returns:
     *   {-1, -1} if qdr is empty or invalid
     *   {c,  -1} if qdr is a column QDR
     *   {-1,  r} if qdr is a row QDR
     *   {c,   r} if qdr is a databody QDR
     * where r and c are the row number and column number of the QDR
     */
    public int[] getSlicesFromQDR(QDRInterface qdr, int[] startSlices, int[] endSlices) {
        // Put QDR's map entries in order by key, which orders them by edge and layer
        SortedMap<String, Object> sortedMap = new TreeMap<String, Object>(qdr);

        // Initialize both elements of the return value to -1.  This guarantees that if the
        // QDR is empty, {-1,-1} is returned as desired, and that if one of the edges doesn't
        // participate in the QDR, -1 is returned in that position as desired.  Thus, all 4
        // cases mentioned in the javadoc above are handled correctly.
        int[] returnValue = new int[] { -1, -1 };

        int parentEdge = -1; // initialize to -1 so that the first pass thru the loop knows it is handling a new edge

        // These initial values are never seen, but must initialize to make compiler happy.
        int parentLayer = -1;
        int parentSlice = -1;
        int parentDepth = -1;
        int parentExtent = -1;

        for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
            String key = entry.getKey();

            // Verify high-level format of key:  EdgeXLayerXX
            if ((!key.startsWith("Edge")) || key.indexOf("Layer") != 5 || key.length() != 12)
                return new int[] { -1, -1 };

            // Get indices from QDR
            int edge;
            int layer;
            int slice;
            try {
                edge = Integer.valueOf(key.substring(4, 5));
                layer = Integer.valueOf(key.substring(10));
                slice = Integer.valueOf(entry.getValue().toString());
            } catch (NumberFormatException e) { // valueOf() exception indicates malformed QDR
                return new int[] { -1, -1 };
            } catch (IndexOutOfBoundsException e) { // substring() exception indicates malformed QDR
                return new int[] { -1, -1 };
            }

            // Verify edge/layer/slice in-bounds and that layer/slice are the start layer/slice.  TBD: what part of this is redundant with the checks in the if/else clause below?
            if (edge < 0 || edge > 1 || layer < 0 || layer >= _getLayerCount(edge) ||
                layer != _getMemberStartLayer(edge, layer, slice) || slice < 0 || slice >= _getEdgeExtent(edge) ||
                slice != _getMemberStartSlice(edge, layer, slice)) {
                return new int[] { -1, -1 };
            }

            // If this is the first member on the edge, verify that it starts in layer 0
            if (parentEdge != edge) {
                if (layer != 0)
                    return new int[] { -1, -1 };
                parentEdge = edge;

                // If this is not the first member on the edge, verify that it starts in the layer immediately
                // following the previous (parent) member, and that the slice is consistent with being a child of the parent
            } else {
                if (parentLayer + parentDepth != layer || slice < parentSlice || slice >= parentSlice + parentExtent) {
                    return new int[] { -1, -1 };
                }
            }

            // set parent values for the next iteration
            parentLayer = layer;
            parentSlice = slice;
            parentDepth = _getMemberDepth(edge, layer, slice);
            parentExtent = _getMemberExtent(edge, layer, slice);

            // Unless the QDR proves invalid, the return value for each edge should be the startSlice
            // of the innermost member on that edge that is represented in the QDR.  Order of iteration
            // guarantees that the last member processed for an edge is that innermost member, so we set
            // the return value on each iteration so that the last one is kept.
            returnValue[edge] = slice;
        }

        return returnValue;
    }

    public int getMemberSiblingCount(int edge, int[] hPos, int memberLayer) throws EdgeOutOfRangeException,
                                                                                   LayerOutOfRangeException,
                                                                                   SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public Object getMemberMetadata(int edge, int[] hPos, int memberLayer, int hIndex,
                                    String type) throws EdgeOutOfRangeException, LayerOutOfRangeException,
                                                        SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public int[] getMemberHPos(int edge, int layer, int slice) throws EdgeOutOfRangeException,
                                                                      LayerOutOfRangeException,
                                                                      SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public int[] getFirstHPos(int edge) throws EdgeOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public int[] getLastHPos(int edge) throws EdgeOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public int[] getPrevHPos(int edge, int[] hPos) throws EdgeOutOfRangeException, LayerOutOfRangeException,
                                                          SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public int[] getNextHPos(int edge, int[] hPos) throws EdgeOutOfRangeException, LayerOutOfRangeException,
                                                          SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public int findMember(int edge, int[] hPos, int memberLayer, String s, String type,
                          int flags) throws EdgeOutOfRangeException, LayerOutOfRangeException,
                                            SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public void release() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public void startGroupEdit() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public void endGroupEdit() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public boolean submitChanges() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public boolean undoEdit() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public boolean redoEdit() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public boolean dropChanges() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public void setAutoSubmit(boolean bValue) {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public boolean isAutoSubmit() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public Vector getQDRoverrideCollection() {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    // ****************   UNIMPLEMENTED DATAACCESS2 METHODS   ******************

    public List getUniqueMemberMetadata(int edge, int layer, String[] types, int start,
                                        int count) throws EdgeOutOfRangeException, LayerOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public List getUniqueMemberMetadata(String layerName, String[] types, int start,
                                        int count) throws LayerOutOfRangeException, SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public List getCorrespondingMemberMetadata(int edge, int layer, String[] values, String[] types,
                                               boolean inDataAccess) throws EdgeOutOfRangeException,
                                                                            LayerOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public List getCorrespondingMemberMetadata(String layerName, String[] values, String[] types,
                                               boolean inDataAccess) throws LayerOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public List getUniqueDataValues(String name, String[] types, int start, int count) throws LayerOutOfRangeException,
                                                                                              SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public boolean allSlicesFetched(int edge) throws EdgeOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    // ****************   UNIMPLEMENTED DATAACCESS3 METHODS   ******************

    public boolean isFetched(int[] startSlice, int[] endSlice, int flag) throws SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public boolean forceFetch(int[] startSlice, int[] endSlice, int flag) throws SliceOutOfRangeException {
        throw new UnsupportedOperationException("This test class does not implement this method.");
    }

    public DataMap getSupportedDataMap() {
        String supported[] = { DataMap.DATA_UNFORMATTED };
        return new DataMap(supported);
    }

    public MetadataMap getSupportedMetadataMap() {
        String supported[] = { MetadataMap.METADATA_LONGLABEL, MetadataMap.METADATA_DRILLSTATE };
        return new MetadataMap(supported);
    }

    public LayerMetadataMap getSupportedLayerMetadataMap() {
        String supported[] = { LayerMetadataMap.LAYER_METADATA_LONGLABEL };
        return new LayerMetadataMap(supported);
    }

    /**
     * Returns a QDR in which each member is named after its start slice, e.g.
     * {"Edge0Layer00":"9";"Edge0Layer01":"12";"Edge0Layer02":"13"}
     */
    public QDR getMemberQDR(int edge, int layer, int slice, int flags) throws EdgeOutOfRangeException,
                                                                              LayerOutOfRangeException,
                                                                              SliceOutOfRangeException {
        checkEdge(edge);
        checkLayer(edge, layer);
        checkSlice(edge, slice);

        return _getMemberQDR(edge, layer, slice, flags);
    }

    // This version doesn't check whether the params are in-bounds.  The main version does that.

    private QDR _getMemberQDR(int edge, int layer, int slice, int flags) {
        QDR qdr = new QDR();

        for (int lyr = 0; lyr <= layer; ++lyr) { // note <=
            if (_getMemberStartLayer(edge, lyr, slice) != lyr)
                continue;
            String layerName = _getLayerMetadata(edge, lyr, LayerMetadataMap.LAYER_METADATA_LONGLABEL);
            int startSlice = _getMemberStartSlice(edge, lyr, slice);
            qdr.addDimMemberPair(layerName, Integer.toString(startSlice));
        }
        return qdr;
    }

    public QDR getValueQDR(int row, int column, int flags) throws RowOutOfRangeException, ColumnOutOfRangeException {
        checkRowColumn(row, column);
        QDR rowQdr = _getSliceQDR(DataDirector.ROW_EDGE, row, flags);
        QDR colQdr = _getSliceQDR(DataDirector.COLUMN_EDGE, column, flags);

        // combine the two slice QDR's into a single QDR.  We have to do it manually since QDR doesn't seem to support any useful bulk operations like putAll().
        for (Object entryObj : colQdr.entrySet()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>)entryObj;
            rowQdr.addDimMemberPair(entry.getKey(), entry.getValue());
        }

        return rowQdr; // now contains the column entries too
    }

    public QDR getSliceQDR(int edge, int slice, int flags) throws EdgeOutOfRangeException, SliceOutOfRangeException {
        checkEdge(edge);
        checkSlice(edge, slice);
        return _getSliceQDR(edge, slice, flags);
    }

    // This version doesn't check whether the params are in-bounds.  The main version does that.

    private QDR _getSliceQDR(int edge, int slice, int flags) {
        return _getMemberQDR(edge, _getLayerCount(edge) - 1, slice, flags);
    }


    protected DataDirectorListener listener;
    private HashMap m_updatedValues = new HashMap();
}
