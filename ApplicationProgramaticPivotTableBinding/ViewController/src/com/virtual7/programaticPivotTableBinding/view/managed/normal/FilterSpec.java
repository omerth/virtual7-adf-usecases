package com.virtual7.programaticPivotTableBinding.view.managed.normal;

public class FilterSpec {
    public FilterSpec(String label, int columnIndex, double minValue, double maxValue, Number rangeMinValue, Number rangeMaxValue) 
    {
        _label = label;
        _columnIndex = columnIndex;
        _minValue = (long)minValue;
        _maxValue = (long)maxValue;
        _rangeMinValue = rangeMinValue.longValue();
        _rangeMaxValue = rangeMaxValue.longValue();
        _dataEnabled=true;
    }
    
    public boolean includeRow(Object[] row) {
        Object _value = row[_columnIndex];
        if(_value instanceof Number)
        {
            double value = ((Number)_value).doubleValue();
            if(value < _rangeMinValue.doubleValue() ||
               value > _rangeMaxValue.doubleValue())
                return false;
        }
        return true;
    }

    public void setRangeMax(Number rangeMaxValue) {
        _rangeMaxValue=rangeMaxValue;
    }

    public void setRangeMin(Number rangeMinValue) {
        _rangeMinValue=rangeMinValue;
    }

    public void setMax(Number maxValue) {
        _maxValue=maxValue;
    }

    public void setMin(Number minValue) {
        _minValue=minValue;
    }

    public Number getRangeMax() {
        return _rangeMaxValue;
    }

    public Number getRangeMin() {
        return _rangeMinValue;
    }
    
    public String getLabel() {
        return _label;
    }
    public Number getMin() {
        return _minValue;
    }

    public Number getMax() {
        return _maxValue;
    }
    
    public void setDataEnabled(Boolean dataEnabled) {
        _dataEnabled = dataEnabled;
    }
    public Boolean isDataEnabled() {
        return _dataEnabled;
    }
    
    String _label;
    int _columnIndex;
    Number _minValue;
    Number _maxValue;
    Number _rangeMinValue;
    Number _rangeMaxValue;
    Boolean _dataEnabled;
}