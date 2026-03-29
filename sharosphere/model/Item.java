package sharosphere.model;

public class Item {
    public enum Category { TEXTBOOKS, CAD_TOOLS, LAB_EQUIPMENT, CALCULATORS, STATIONERY, OTHER }
    public enum Type { FREE, PAID }

    private String title;
    private String description;
    private Category category;
    private Type type;
    private double price;
    private String seller;
    private String college;
    private String contact;
    private String condition;
    private String emoji;

    public Item(String title, String description, Category category, Type type,
                double price, String seller, String college, String contact, String condition) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.type = type;
        this.price = price;
        this.seller = seller;
        this.college = college;
        this.contact = contact;
        this.condition = condition;
        this.emoji = resolveEmoji(category);
    }

    private String resolveEmoji(Category c) {
        switch (c) {
            case TEXTBOOKS:      return "\uD83D\uDCDA"; // 📚
            case CAD_TOOLS:      return "\uD83D\uDCD0"; // 📐
            case LAB_EQUIPMENT:  return "\uD83D\uDD2C"; // 🔬
            case CALCULATORS:    return "\uD83E\uDDF2"; // 🧮
            case STATIONERY:     return "\u270F\uFE0F"; // ✏️
            default:             return "\uD83D\uDCE6"; // 📦
        }
    }

    public String getTitle()       { return title; }
    public String getDescription() { return description; }
    public Category getCategory()  { return category; }
    public Type getType()          { return type; }
    public double getPrice()       { return price; }
    public String getSeller()      { return seller; }
    public String getCollege()     { return college; }
    public String getContact()     { return contact; }
    public String getCondition()   { return condition; }
    public String getEmoji()       { return emoji; }
    public boolean isFree()        { return type == Type.FREE; }
}
