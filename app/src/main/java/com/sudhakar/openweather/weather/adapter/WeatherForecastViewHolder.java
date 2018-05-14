package com.sudhakar.openweather.weather.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sudhakar.openweather.R;
import com.sudhakar.openweather.utils.Utils;
import com.sudhakar.openweather.weather.model.WeatherForecast;
import com.sudhakar.openweather.weather.view.ForecastBottomSheetDialogFragment;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherForecastViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener {

    private final String TAG = "ForecastViewHolder";

    private WeatherForecast mWeatherForecast;
    private Context mContext;
    private FragmentManager mFragmentManager;

    private TextView mDateTime;
    private TextView mIcon;
    private TextView mTemperatureMin;
    private TextView mTemperatureMax;

    public WeatherForecastViewHolder(View itemView, Context context,
                                     FragmentManager fragmentManager) {
        super(itemView);
        mContext = context;
        mFragmentManager = fragmentManager;
        itemView.setOnClickListener(this);

        mDateTime = itemView.findViewById(R.id.forecast_date_time);
        mIcon = itemView.findViewById(R.id.forecast_icon);
        mTemperatureMin = itemView.findViewById(R.id.forecast_temperature_min);
        mTemperatureMax = itemView.findViewById(R.id.forecast_temperature_max);
    }

    void bindWeather(WeatherForecast weather) {
        mWeatherForecast = weather;

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),
                                                     "fonts/weathericons-regular-webfont.ttf");
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMMM", Locale.getDefault());
        Date date = new Date(weather.getDateTime() * 1000);
        String temperatureMin = mContext.getString(R.string.temperature_with_degree,
                                                   String.format(Locale.getDefault(), "%.0f",
                                                                 weather.getTemperatureMin()));
        String temperatureMax = mContext.getString(R.string.temperature_with_degree,
                                                   String.format(Locale.getDefault(), "%.0f",
                                                                 weather.getTemperatureMax()));

        mDateTime.setText(format.format(date));
        mIcon.setTypeface(typeface);
        mIcon.setText(Utils.getStrIcon(mContext, weather.getIcon()));
        if (weather.getTemperatureMin() > 0) {
            temperatureMin = "+" + temperatureMin;
        }
        mTemperatureMin.setText(temperatureMin);
        if (weather.getTemperatureMax() > 0) {
            temperatureMax = "+" + temperatureMax;
        }
        mTemperatureMax.setText(temperatureMax);
    }

    @Override
    public void onClick(View view) {
        new ForecastBottomSheetDialogFragment()
                .newInstance(mWeatherForecast)
                .show(mFragmentManager, "forecastBottomSheet");
    }
}