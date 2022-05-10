package me.zhengjie.algorithm.service.impl;

import lombok.RequiredArgsConstructor;
import net.seninp.jmotif.sax.discord.BruteForceDiscordImplementation;
import net.seninp.jmotif.sax.discord.DiscordRecords;
import net.seninp.jmotif.sax.registry.LargeWindowAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BruteForceAlgorithmImpl extends AlgorithmImpl {
    @Override
    public DiscordRecords series2AnomaliesRecords(double[] series, int windowSize, Map<String, String> params) {
        DiscordRecords discordRecords = null;
        try {
            discordRecords = BruteForceDiscordImplementation.series2BruteForceDiscords(series, windowSize, Integer.parseInt(params.get(DISCORDS_TO_TEST)), new LargeWindowAlgorithm(), Double.parseDouble(params.get(NORM_THRESHOLD)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return discordRecords;
    }
}
