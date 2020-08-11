package com.bigtechsolutions.toniclifefenix.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bigtechsolutions.toniclifefenix.data.dao.ShoppingCartDao;
import com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart;

@Database(entities = {ShoppingCart.class}, version = 2)
public abstract class ShoppingCartDataBase extends RoomDatabase {
    public abstract ShoppingCartDao shoppingCartDao();
    private static volatile ShoppingCartDataBase INSTANCE;

    public static ShoppingCartDataBase getDatabase(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (ShoppingCartDataBase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ShoppingCartDataBase.class, "shopping_cart_database")
                            .build();
                }
            }
        }

        return INSTANCE;
    }

}
