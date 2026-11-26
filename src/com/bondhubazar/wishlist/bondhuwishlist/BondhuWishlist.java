
package com.bondhubazar.wishlist.bondhuwishlist;

// ArrayList ব্যবহার করার জন্য
import java.util.ArrayList;

// LinkedHashSet ব্যবহার করার জন্য
import java.util.LinkedHashSet;

// App Inventor প্রয়োজনীয় ক্লাস
import com.google.appinventor.components.annotations.Extension;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleEvent;

import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.util.YailList;

@Extension(
    description = "Bondhu Bazar Wishlist Manager"
)

public class BondhuWishlist extends AndroidNonvisibleComponent {

    // Wishlist-এর Product ID রাখবে
    // LinkedHashSet একই ID দ্বিতীয়বার যোগ হতে দেবে না
    // এবং যোগ করার ক্রম বজায় রাখবে
    private LinkedHashSet<String> wishlist =
        new LinkedHashSet<>();

    // Constructor
    public BondhuWishlist(ComponentContainer container) {

        super(container.$form());
    }

    // ==================================
    // ১. Wishlist-এ Product যোগ করা
    // ==================================

    @SimpleFunction(
        description = "Add product to wishlist"
    )
    public boolean AddToWishlist(String productId) {

        // খালি ID গ্রহণ করা হবে না
        if (productId == null || productId.trim().isEmpty()) {

            ErrorOccurred("Product ID cannot be empty");

            return false;
        }

        // Product ID পরিষ্কার করা
        String id = productId.trim();

        // আগে থেকেই থাকলে আবার যোগ হবে না
        if (wishlist.contains(id)) {

            return false;
        }

        // Wishlist-এ যোগ করা
        wishlist.add(id);

        // Event পাঠানো
        ProductAdded(id);

        return true;
    }

    // ==================================
    // ২. Wishlist থেকে Product সরানো
    // ==================================

    @SimpleFunction(
        description = "Remove product from wishlist"
    )
    public boolean RemoveFromWishlist(String productId) {

        if (productId == null || productId.trim().isEmpty()) {

            ErrorOccurred("Invalid product ID");

            return false;
        }

        String id = productId.trim();

        // Product সরানো
        boolean removed = wishlist.remove(id);

        if (removed) {

            ProductRemoved(id);
        }

        return removed;
    }

    // ==================================
    // ৩. Product Wishlist-এ আছে কি না
    // ==================================

    @SimpleFunction(
        description = "Check whether product is in wishlist"
    )
    public boolean IsInWishlist(String productId) {

        if (productId == null) {
            return false;
        }

        return wishlist.contains(productId.trim());
    }

    // ==================================
    // ৪. Wishlist-এর সব Product ID
    // ==================================

    @SimpleFunction(
        description = "Get all wishlist product IDs"
    )
    public YailList GetWishlist() {

        ArrayList<String> ids = new ArrayList<>();

        ids.addAll(wishlist);

        return YailList.makeList(ids);
    }

    // ==================================
    // ৫. Wishlist-এর মোট Product সংখ্যা
    // ==================================

    @SimpleFunction(
        description = "Get wishlist product count"
    )
    public int GetWishlistCount() {

        return wishlist.size();
    }

    // ==================================
    // ৬. Wishlist খালি কি না
    // ==================================

    @SimpleFunction(
        description = "Check whether wishlist is empty"
    )
    public boolean IsWishlistEmpty() {

        return wishlist.isEmpty();
    }

    // ==================================
    // ৭. সম্পূর্ণ Wishlist পরিষ্কার
    // ==================================

    @SimpleFunction(
        description = "Clear all wishlist products"
    )
    public void ClearWishlist() {

        wishlist.clear();

        WishlistCleared();
    }

    // ==================================
    // ৮. Wishlist Updated Event
    // ==================================

    @SimpleEvent(
        description = "Triggered when product is added"
    )
    public void ProductAdded(String productId) {

        EventDispatcher.dispatchEvent(
            this,
            "ProductAdded",
            productId
        );
    }

    // ==================================
    // ৯. Product Removed Event
    // ==================================

    @SimpleEvent(
        description = "Triggered when product is removed"
    )
    public void ProductRemoved(String productId) {

        EventDispatcher.dispatchEvent(
            this,
            "ProductRemoved",
            productId
        );
    }

    // ==================================
    // ১০. Wishlist Cleared Event
    // ==================================

    @SimpleEvent(
        description = "Triggered when wishlist is cleared"
    )
    public void WishlistCleared() {

        EventDispatcher.dispatchEvent(
            this,
            "WishlistCleared"
        );
    }

    // ==================================
    // ১১. Error Event
    // ==================================

    @SimpleEvent(
        description = "Triggered when an error occurs"
    )
    public void ErrorOccurred(String message) {

        EventDispatcher.dispatchEvent(
            this,
            "ErrorOccurred",
            message
        );
    }
}
