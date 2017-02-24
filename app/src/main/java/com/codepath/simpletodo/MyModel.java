package com.codepath.simpletodo;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.TypeConverter;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * Created by alex_ on 2/21/2017.
 */

@Table(database = AppDatabase.class,name = "MyModel_Table")
public class MyModel extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    int id;

    @Column
    String name;

    @Column
    String description;

    @Column
    String priority;

    @Column
    Date createdate;

    @Column
    Date duedate;
}
