package com.reail.point;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.reail.point.model.User;

public class MarkerClusterRenderer extends DefaultClusterRenderer<User> {
    private static final int MARKER_DIMENSION = 68;  // 2

    private final IconGenerator iconGenerator;
    private final ImageView markerImageView;
    private Context context;

    public MarkerClusterRenderer(Context context, GoogleMap map, ClusterManager<User> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
        iconGenerator = new IconGenerator(context);  // 3
        markerImageView = new ImageView(context);
        markerImageView.setLayoutParams(new ViewGroup.LayoutParams(MARKER_DIMENSION, MARKER_DIMENSION));
        iconGenerator.setContentView(markerImageView);  // 4

    }

    @Override
    protected void onBeforeClusterItemRendered(User item, MarkerOptions markerOptions) { // 5
        if (!Constant.subscribed) {
            markerImageView.setImageResource(getimgRes(item.getIndex()));
        } else {
            markerImageView.setImageResource(subUser(item.getIndex()));
        } // 6
        Bitmap icon = iconGenerator.makeIcon();  // 7
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));  // 8
        markerOptions.title(item.getTitle());
    }

    private int subUser(int pos) {
        int res = R.drawable.green43;
        int currentCapacity1 = 0;
        try {
            currentCapacity1 = Integer.parseInt(MapsMarker.locData.get(pos).getOutputKW());
        } catch (Exception e) {
        }
        int currentCapacity2 = 0;
        try {
            currentCapacity2 = Integer.parseInt(MapsMarker.locData.get(pos).getConnector2OutputKW());
        } catch (Exception e) {
        }
        int currentCapacity3 = 0;
        try {
            currentCapacity3 = Integer.parseInt(MapsMarker.locData.get(pos).getConnector3OutputKW());
        } catch (Exception e) {
        }
        int currentCapacity4 = 0;
        try {
            currentCapacity4 = Integer.parseInt(MapsMarker.locData.get(pos).getConnector4OutputKW());
        } catch (Exception e) {
        }
        int maxValue = currentCapacity1;
        if (currentCapacity2 > maxValue) {
            maxValue = currentCapacity2;
        }
        if (currentCapacity3 > maxValue) {
            maxValue = currentCapacity3;
        }
        if (currentCapacity4 > maxValue) {
            maxValue = currentCapacity4;
        }
        try {
            String rating = MapsMarker.locData.get(pos).getReliabilityRating();
            if (rating.equalsIgnoreCase("")) {
                int companyRatingValue = getCompanyReliaablityPoint(MapsMarker.locData.get(pos).getDeviceNetworks());
                if (companyRatingValue >= 0 && companyRatingValue <= 4) {
                    if (maxValue <= 7) {
                        res = R.drawable.amber7;
                    } else if (maxValue > 7 && maxValue <= 22) {
                        res = R.drawable.amber22;
                    } else if (maxValue > 22 && maxValue <= 43) {
                        res = R.drawable.amber43;
                    } else if (maxValue > 43) {
                        res = R.drawable.amber50pluse;
                    }
                } else if (companyRatingValue > 4 && companyRatingValue <= 6) {
                    if (maxValue <= 7) {
                        res = R.drawable.amber7;
                    } else if (maxValue > 7 && maxValue <= 22) {
                        res = R.drawable.amber22;
                    } else if (maxValue > 22 && maxValue <= 43) {
                        res = R.drawable.amber43;
                    } else if (maxValue > 43) {
                        res = R.drawable.amber50pluse;
                    }

                } else {
                    if (maxValue <= 7) {
                        res = R.drawable.green7;
                    } else if (maxValue > 7 && maxValue <= 22) {
                        res = R.drawable.green22;
                    } else if (maxValue > 22 && maxValue <= 43) {
                        res = R.drawable.green43;
                    } else if (maxValue > 43) {
                        res = R.drawable.green50pluse;
                    }
                }
            } else if (rating.equalsIgnoreCase("1")) {
                if (maxValue <= 7) {
                    res = R.drawable.red7;
                } else if (maxValue > 7 && maxValue <= 22) {
                    res = R.drawable.red22;
                } else if (maxValue > 22 && maxValue <= 43) {
                    res = R.drawable.red43;
                } else if (maxValue > 43) {
                    res = R.drawable.red50pluse;
                }
            } else if (rating.equalsIgnoreCase("2")) {
                if (maxValue <= 7) {
                    res = R.drawable.amber7;
                } else if (maxValue > 7 && maxValue <= 22) {
                    res = R.drawable.amber22;
                } else if (maxValue > 22 && maxValue <= 43) {
                    res = R.drawable.amber43;
                } else if (maxValue > 43) {
                    res = R.drawable.amber50pluse;
                }
            } else if (rating.equalsIgnoreCase("3")) {
                if (maxValue <= 7) {
                    res = R.drawable.green7;
                } else if (maxValue > 7 && maxValue <= 22) {
                    res = R.drawable.green22;
                } else if (maxValue > 22 && maxValue <= 43) {
                    res = R.drawable.green43;
                } else if (maxValue > 43) {
                    res = R.drawable.green50pluse;
                }
            }
        } catch (Exception e) {
            Log.e("Error ::", e.getLocalizedMessage());
        }
        return res;
    }

    private int getCompanyReliaablityPoint(String chargertype) {

        int companyRatingValue = 0;
        if (chargertype.contains("Tesla")) {
            companyRatingValue = 8;
        } else if (chargertype.contains("InstaVolt Ltd")) {
            companyRatingValue = 9;
        } else if (chargertype.contains("Osprey")) {
            companyRatingValue = 7;
        } else if (chargertype.contains("Shell Recharge")) {
            companyRatingValue = 7;
        } else if (chargertype.contains("POD Point")) {
            companyRatingValue = 6;
        } else if (chargertype.contains("Grid serve")) {
            companyRatingValue = 6;
        } else if (chargertype.contains("Ionity")) {
            companyRatingValue = 6;
        } else if (chargertype.contains("Engie")) {
            companyRatingValue = 6;
        } else if (chargertype.contains("ChargePlace Scotland")) {
            companyRatingValue = 5;
        } else if (chargertype.contains("The GeniePoint Network")) {
            companyRatingValue = 6;
        } else if (chargertype.contains("Charge Your Car")) {
            companyRatingValue = 3;
        } else if (chargertype.contains("Source London")) {
            companyRatingValue = 6;
        } else if (chargertype.contains("Chargemaster (POLAR)") || chargertype.contains("Plugged-in Midlands") || chargertype.contains("Milton Keyne") || chargertype.contains("BP pulse")) {
            companyRatingValue = 5;
        } else if (chargertype.contains("GMEV")) {
            companyRatingValue = 7;
        } else if (chargertype.contains("ecars ESB")) {
            companyRatingValue = 7;
        } else if (chargertype.contains("Ecotricity (Electric Highway")) {
            companyRatingValue = 7;
        } else {
            companyRatingValue = 7;
        }
        return companyRatingValue;
    }

    private int getimgRes(int pos) {
        int res = R.drawable.green43;
        int currentCapacity1 = 0;
        try {
            currentCapacity1 = Integer.parseInt(MapsMarker.locData.get(pos).getOutputKW());
        } catch (Exception e) {
        }
        int currentCapacity2 = 0;
        try {
            currentCapacity2 = Integer.parseInt(MapsMarker.locData.get(pos).getConnector2OutputKW());
        } catch (Exception e) {
        }
        int currentCapacity3 = 0;
        try {
            currentCapacity3 = Integer.parseInt(MapsMarker.locData.get(pos).getConnector3OutputKW());
        } catch (Exception e) {
        }
        int currentCapacity4 = 0;
        try {
            currentCapacity4 = Integer.parseInt(MapsMarker.locData.get(pos).getConnector4OutputKW());
        } catch (Exception e) {
        }
        int maxValue = currentCapacity1;
        if (currentCapacity2 > maxValue) {
            maxValue = currentCapacity2;
        }
        if (currentCapacity3 > maxValue) {
            maxValue = currentCapacity3;
        }
        if (currentCapacity4 > maxValue) {
            maxValue = currentCapacity4;
        }
        try {
            if (MapsMarker.locData.get(pos).getChargeDeviceStatus().equalsIgnoreCase("In service")) {
                if (maxValue <= 7) {
                    res = R.drawable.green7;
                } else if (maxValue > 7 && maxValue <= 22) {
                    res = R.drawable.green22;
                } else if (maxValue > 22 && maxValue <= 43) {
                    res = R.drawable.green43;
                } else if (maxValue > 43) {
                    res = R.drawable.green50pluse;
                }
            } else {
                if (maxValue <= 7) {
                    res = R.drawable.red7;
                } else if (maxValue > 7 && maxValue <= 22) {
                    res = R.drawable.red22;
                } else if (maxValue > 22 && maxValue <= 43) {
                    res = R.drawable.red43;
                } else if (maxValue > 43) {
                    res = R.drawable.red50pluse;
                }
            }
        } catch (Exception e) {
            Log.e("Error ::", e.getLocalizedMessage());
        }
        return res;
    }
}
