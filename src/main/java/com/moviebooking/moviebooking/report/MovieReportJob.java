package com.moviebooking.moviebooking.report;
import com.moviebooking.moviebooking.service.MovieBookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;


@Slf4j
public class MovieReportJob {
   private  final MovieBookingService movieBookingService;

   public MovieReportJob(MovieBookingService movieBookingService){
       this.movieBookingService = movieBookingService;
   }

   @Scheduled
   public void generateReport(){
       log.info("Generating movie report");
       movieBookingService.generateReport();
       log.info("Report generated at " + java.time.LocalDateTime.now());

   }
}
