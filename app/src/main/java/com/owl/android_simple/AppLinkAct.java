package com.owl.android_simple;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Description
 *
 * @author zhangyunxiang Date 2021/8/27 11:43
 */
public class AppLinkAct extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent appLinkIntent = getIntent();
    String appLinkAction = appLinkIntent.getAction();
    Uri appLinkData = appLinkIntent.getData();
    if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null) {
      String recipeId = appLinkData.getLastPathSegment();
      Uri appData =
          Uri.parse("content://com.recipe_app/recipe/").buildUpon().appendPath(recipeId).build();
      // showRecipe(appData);
    }
  }
}
