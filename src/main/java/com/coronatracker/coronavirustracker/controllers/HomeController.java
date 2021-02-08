package com.coronatracker.coronavirustracker.controllers;

import com.coronatracker.coronavirustracker.models.LocationStats;
import com.coronatracker.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DecimalFormat;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPreviousDay()).sum();

        LocationStats locationStats = new LocationStats();

        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", locationStats.formatNumber(totalReportedCases));
        model.addAttribute("totalNewCases", locationStats.formatNumber(totalNewCases));

        return "home";
    }
}
