package tuomomees.screentimecalculator;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by tuomo on 6.6.2017.
 */

public class Top5AppsFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;

    //TextViewit, joihin asetetaan näkyviin top5 applikaatioiden infot
    TextView top1AppText;
    TextView top2AppText;
    TextView top3AppText;
    TextView top4AppText;
    TextView top5AppText;

    //ImageViewit, joihin asetetaan näkyviin applikaatioiden ikonit
    ImageView top1Icon;
    ImageView top2Icon;
    ImageView top3Icon;
    ImageView top4Icon;
    ImageView top5Icon;

    //Muuttujat, jotka sisältävät Applikaation nimen, top5 numeron ja käyttöajan
    public String top1AppInfo;
    public String top2AppInfo;
    public String top3AppInfo;
    public String top4AppInfo;
    public String top5AppInfo;

    //Muuttujat, jotka sisältävät applikaation paketin nimen
    String top1Package;
    String top2Package;
    String top3Package;
    String top4Package;
    String top5Package;

    //Drawablet, joihin tulee applikaatioiden ikonit
    Drawable icon1 = null;
    Drawable icon2 = null;
    Drawable icon3 = null;
    Drawable icon4 = null;
    Drawable icon5 = null;

    View view;

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //page = getArguments().getInt("someInt", 0);
        //title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_top5apps, container, false);

        //Alustetaan widgetit
        initialize();

        //Haetaan tarvittavat jaetut tiedot (paketin nimi ja app -info)
        getSharedPreferences();

        //Asetetaan tekstit, mikäli ne eivät ole tyhjiä
        if(!top1AppInfo.equalsIgnoreCase("") && !top2AppInfo.equalsIgnoreCase("") && !top3AppInfo.equalsIgnoreCase("") && !top4AppInfo.equalsIgnoreCase("") && !top5AppInfo.equalsIgnoreCase(""))
        {
            top1AppText.setText(top1AppInfo);
            top2AppText.setText(top2AppInfo);
            top3AppText.setText(top3AppInfo);
            top4AppText.setText(top4AppInfo);
            top5AppText.setText(top5AppInfo);
            Log.d("Tekstit asetettu", "top5");
        }

        //Asetetaan TOP5 appsien iconit näkymään, mikäli arvot eivät ole NULL
        if(top1Package != null && top2Package != null && top3Package != null && top4Package != null && top5Package != null)
        {
            icon1 = getIconDrawable(top1Package);
            icon2 = getIconDrawable(top2Package);
            icon3 = getIconDrawable(top3Package);
            icon4 = getIconDrawable(top4Package);
            icon5 = getIconDrawable(top5Package);

            if(icon1 != null && icon2 != null && icon3 != null && icon4 != null && icon5 != null)
            {
                top1Icon.setImageDrawable(icon1);
                top2Icon.setImageDrawable(icon2);
                top3Icon.setImageDrawable(icon3);
                top4Icon.setImageDrawable(icon4);
                top5Icon.setImageDrawable(icon5);
                Log.d("Asetetaan ikonit", "OK");
            }
        }

         //view = inflater.inflate(R.layout.fragment_top5apps, container, false);
         //pagerHeader = (ViewPager) view.findViewById(R.id.vpPager);

         //tvLabel.setText(page + " -- " + title);

        return view;
        //return inflater.inflate(R.layout.fragment_top5apps, container, false);
    }

    //Metodi, jolla alustetaan tarvittavat widgetit
    protected void initialize()
    {
        //Alustetaan TextViewit
        top1AppText= (TextView) view.findViewById(R.id.top1App);
        top2AppText= (TextView) view.findViewById(R.id.top2App);
        top3AppText= (TextView) view.findViewById(R.id.top3App);
        top4AppText= (TextView) view.findViewById(R.id.top4App);
        top5AppText= (TextView) view.findViewById(R.id.top5App);

        //Alustetaan ImageViewit
        top1Icon = (ImageView) view.findViewById(R.id.imageViewTop1);
        top2Icon = (ImageView) view.findViewById(R.id.imageViewTop2);
        top3Icon = (ImageView) view.findViewById(R.id.imageViewTop3);
        top4Icon = (ImageView) view.findViewById(R.id.imageViewTop4);
        top5Icon = (ImageView) view.findViewById(R.id.imageViewTop5);
    }

    //Metodi, jolla haetaan jaetut tiedot
    protected void getSharedPreferences()
    {
        //Haetaan applikaatioiden tiedot SharedPreferencen avulla String muuttujiin
        SharedPreferences pref = this.getActivity().getSharedPreferences("top5", MODE_PRIVATE);
        top1AppInfo = pref.getString("top1AppInfo", "");
        top2AppInfo = pref.getString("top2AppInfo", "");
        top3AppInfo = pref.getString("top3AppInfo", "");
        top4AppInfo = pref.getString("top4AppInfo", "");
        top5AppInfo = pref.getString("top5AppInfo", "");

        //Haetaan pakettitiedot ikoneita varten
        top1Package = pref.getString("top1AppPackage", null);
        top2Package = pref.getString("top2AppPackage", null);
        top3Package = pref.getString("top3AppPackage", null);
        top4Package = pref.getString("top4AppPackage", null);
        top5Package = pref.getString("top5AppPackage", null);

        /*
        Log.d("top1", top1Package);
        Log.d("top2", top2Package);
        Log.d("top3", top3Package);
        Log.d("top4", top4Package);
        Log.d("top5", top5Package);
        */

        Log.d("Jaetut tiedot haettu", "OK");
    }

    //Metodi, jolla voi hakea tarvittavien applikaatioiden app-ikonit paketin nimen avulla
    protected Drawable getIconDrawable(String packageName) {
        Drawable icon = null;
        try {
            icon = getActivity().getPackageManager().getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return icon;
    }

    // newInstance constructor for creating fragment with arguments
    public static Top5AppsFragment newInstance(int page, String title) {
        Top5AppsFragment top5AppsObj = new Top5AppsFragment();
        Bundle args = new Bundle();

        args.putInt("someInt", page);
        args.putString("someTitle", title);
        top5AppsObj.setArguments(args);
        return top5AppsObj;
    }
}