package com.flj.latte.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * Created by gg
 */

public class  MultipleItemEntity implements MultiItemEntity,Serializable {

//    private final ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUEUE = new ReferenceQueue<>();
    private final LinkedHashMap<Object, Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
//    private final SoftReference<LinkedHashMap<Object, Object>> FIELDS_REFERENCE =
//            new SoftReference<>(MULTIPLE_FIELDS, ITEM_QUEUE);

    MultipleItemEntity(LinkedHashMap<Object, Object> fields) {
//        FIELDS_REFERENCE.get().putAll(fields);
        MULTIPLE_FIELDS.putAll(fields);
    }

    public static MultipleEntityBuilder builder(){
        return new MultipleEntityBuilder();
    }

    @Override
    public int getItemType() {
//        return (int) FIELDS_REFERENCE.get().get(MultipleFields.ITEM_TYPE);
        return (int) MULTIPLE_FIELDS.get(MultipleFields.ITEM_TYPE);
    }

    @SuppressWarnings("unchecked")
    public final <T> T getField(Object key){
//        return (T) FIELDS_REFERENCE.get().get(key);
        return (T) MULTIPLE_FIELDS.get(key);
    }

    public final LinkedHashMap<?,?> getFields(){
//        return FIELDS_REFERENCE.get();
        return  MULTIPLE_FIELDS;
    }

    public final MultipleItemEntity setField(Object key,Object value){
//        FIELDS_REFERENCE.get().put(key,value);
        MULTIPLE_FIELDS.put(key,value);
        return this;
    }
}
