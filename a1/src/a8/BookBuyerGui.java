package a8;

public interface BookBuyerGui {
  void setAgent(BookBuyerAgent a);
  void show();
  void hide();
  void notifyUser(String message);
  void dispose();
}