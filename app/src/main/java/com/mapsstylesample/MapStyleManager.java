package com.mapsstylesample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MapStyleOptions;

import java.util.TreeMap;

public final class MapStyleManager implements GoogleMap.OnCameraMoveListener {

    private final Context context;
    private final GoogleMap map;
    private final GoogleMap.OnCameraMoveListener onCameraMoveListener;
    private final TreeMap<Float, Integer> styleMap = new TreeMap<>();

    @RawRes
    private int currentMapStyleRes = 0;

    private MapStyleManager(@NonNull Context context, @NonNull GoogleMap map, @Nullable GoogleMap.OnCameraMoveListener onCameraMoveListener) {
        this.context = context;
        this.map = map;
        this.onCameraMoveListener = onCameraMoveListener;

        this.map.setOnCameraMoveListener(this);
    }

    public static @NonNull MapStyleManager attachToMap(@NonNull Context context, @NonNull GoogleMap map, @Nullable GoogleMap.OnCameraMoveListener onCameraMoveListener) {
        return new MapStyleManager(context, map, onCameraMoveListener);
    }

    public static @NonNull MapStyleManager attachToMap(@NonNull Context context, @NonNull GoogleMap map) {
        return new MapStyleManager(context, map, null);
    }

    @Override public void onCameraMove() {
        if (null != onCameraMoveListener) {
            onCameraMoveListener.onCameraMove();
        }
        updateMapStyle();
    }

    public void addStyle(float minZoomLevel, @RawRes int mapStyle) {
        this.styleMap.put(minZoomLevel, mapStyle);
        updateMapStyle();
    }

    private void updateMapStyle() {
        CameraPosition cameraPosition = this.map.getCameraPosition();
        float currentZoomLevel = cameraPosition.zoom;

        for (float key : this.styleMap.descendingKeySet()) {
            if (currentZoomLevel >= key) {
                Integer styleId = this.styleMap.get(key);
                setMapStyle(styleId);
                return;
            }
        }
    }

    private void setMapStyle(@RawRes int styleRes) {
        if (this.currentMapStyleRes != styleRes) {
            MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(this.context, styleRes);
            this.map.setMapStyle(style);
            this.currentMapStyleRes = styleRes;
        }
    }

}
