// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.time.LocalDateTime;

public class ExitReceipt {
   private final ParkingTicket ticket;
   private final LocalDateTime exitTime;
   private final long minutes;
   private final long fee;

   public ExitReceipt(ParkingTicket var1, LocalDateTime var2, long var3, long var5) {
      this.ticket = var1;
      this.exitTime = var2;
      this.minutes = var3;
      this.fee = var5;
   }

   public ParkingTicket getTicket() {
      return this.ticket;
   }

   public LocalDateTime getExitTime() {
      return this.exitTime;
   }

   public long getMinutes() {
      return this.minutes;
   }

   public long getFee() {
      return this.fee;
   }

   public String toString() {
      long var10000 = this.ticket.getTicketNo();
      return "Receipt{ticket=" + var10000 + ", minutes=" + this.minutes + ", fee=Rs " + this.fee + "}";
   }
}
