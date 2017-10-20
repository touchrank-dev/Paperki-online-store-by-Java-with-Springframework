package com.kushnir.paperki.model;

public class DeleteRequest {
    int pnt;

    public DeleteRequest() {
    }

    public int getPnt() {
        return pnt;
    }

    public void setPnt(int pnt) {
        this.pnt = pnt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeleteRequest that = (DeleteRequest) o;

        return pnt == that.pnt;
    }

    @Override
    public int hashCode() {
        return pnt;
    }

    @Override
    public String toString() {
        return "DeleteRequest{" +
                "pnt=" + pnt +
                '}';
    }
}
