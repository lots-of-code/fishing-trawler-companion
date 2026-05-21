package com.lotsofcode.fishingtrawler;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class FishingTrawlerPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(FishingTrawlerPlugin.class);
		RuneLite.main(args);
	}
}
