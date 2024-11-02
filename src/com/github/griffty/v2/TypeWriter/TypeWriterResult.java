package com.github.griffty.v2.TypeWriter;

public class TypeWriterResult {
    private volatile TypeWriterStatus status;
    TypeWriterResult(){
        status = TypeWriterStatus.PROCESSING;
    }
    public TypeWriterStatus getStatus() {
        return status;
    }
    void makeFinished() {
        this.status = TypeWriterStatus.FINISHED;
    }
}
