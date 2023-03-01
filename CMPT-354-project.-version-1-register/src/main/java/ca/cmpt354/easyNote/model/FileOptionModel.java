package ca.cmpt354.easyNote.model;


public class FileOptionModel {
    public Object id;
    public String text;
    public Object value;
    /**
     * 对应下拉框 select 的 name
     */
    public String departmentCode;

    public FileOptionModel() {
        this.id = 0;
        this.text = "";
    }

    public FileOptionModel(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
}
