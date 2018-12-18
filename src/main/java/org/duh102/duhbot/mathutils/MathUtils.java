package org.duh102.duhbot.mathutils;

import java.util.*;
import java.util.regex.*;

import org.pircbotx.hooks.*;
import org.pircbotx.hooks.events.*;

import org.duh102.duhbot.functions.*;

public class MathUtils extends ListenerAdapter implements ListeningPlugin
{
  static java.util.Random random;

  static Pattern utilsPattern = Pattern.compile("^\\.(?<cmd>(?:choice|math))[ \t]+(?<rest>[^\n]+)");
  public Map<String,String> getHelpFunctions()
  {
    Map<String,String> helpFunctions = new HashMap<String,String>();
    helpFunctions.put("choice", "Choose randomly from a list (.choice (choice1) (choice2) )");
    //helpFunctions.put("math", "Perform some math function (.math (mathematical expression) )");
    return helpFunctions;
  }

  public String getPluginName()
  {
    return "Math/Random Utilities";
  }
  
  public ListenerAdapter getAdapter()
  {
    return this;
  }
  
  public MathUtils()
  {
    if(random == null)
    {
      random = new Random();
    }
  }
  
  String message, command, rest;
  public void onMessage(MessageEvent event)
  {
    message = org.pircbotx.Colors.removeFormattingAndColors(event.getMessage()).trim();
    Matcher match = utilsPattern.matcher(message);
    if(!match.matches()){
      return;
    }
    command = match.group("cmd");
    rest = match.group("rest").trim();
    if(command.equals("choice"))
    {
      event.respond(String.format("I chose: %s, just for you!", doChoice(rest)));
    }
  }
  
  String[] choices;
  private String doChoice(String choiceStr) {
    if( choiceStr.contains(",") ) {
      choices = choiceStr.split(",[ \t]*");
    } else {
      choices = choiceStr.split("[ \t]+");
    }
    return choices[random.nextInt(choices.length)];
  }
}