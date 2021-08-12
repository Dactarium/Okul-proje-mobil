package com.eren.siparisuygulamasi.items.order;

import android.widget.Button;

public interface CartButtonListener {
    public abstract void changeAmount(int position, Button button);
    public abstract void removeFromCart(int position);
}
