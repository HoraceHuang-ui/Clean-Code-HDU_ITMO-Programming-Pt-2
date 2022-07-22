import java.util.ArrayList;
import java.util.Date;

public class DiscountDatasList {
    ArrayList<DiscountData> datas = new ArrayList<>();
    ArrayList<String> listSource = new ArrayList<>();
    ArrayList<Integer> filterIds = new ArrayList<>();

    public void add() {
        datas.add(new DiscountData("", "", 0, new Date()));
        listSource.add("");
    }

    /**
     * @return Returns the new id of the edited element after sorting
     */
    public int edit(int id, String name, String shop, int discountPercent, Date date) {
        datas.set(id, new DiscountData(name, shop, discountPercent, date));
        listSource.set(id, String.format("%s (%s, %d%%)", name, shop, discountPercent));

        int i;
        // sort by shop, a-z
        // for (int i = id; !(shops[i] >= shops[i-1] && shops[i] <= shops[i+1]);)
        for (i = id; !((i == 0 || datas.get(i).shop.compareToIgnoreCase(datas.get(i-1).shop) >= 0) && (i == datas.size()-1 || datas.get(i).shop.compareToIgnoreCase(datas.get(i+1).shop) <= 0));) {
            if (i != 0 && datas.get(i).shop.compareToIgnoreCase(datas.get(i-1).shop) < 0) {
                if (filterIds.contains(i - 1)) {
                    filterIds.set(filterIds.indexOf(i - 1), filterIds.get(filterIds.indexOf(i - 1)) + 1);
                }
                DiscountData temp = datas.get(i);
                String temp_s = listSource.get(i);
                datas.set(i, datas.get(i-1));
                listSource.set(i, listSource.get(i-1));
                datas.set(i-1, temp);
                listSource.set(i-1, temp_s);
                i--;
            }
            else if (i != datas.size()-1 && datas.get(i).shop.compareToIgnoreCase(datas.get(i+1).shop) > 0) {
                if (filterIds.contains(i + 1)) {
                    filterIds.set(filterIds.indexOf(i + 1), filterIds.get(filterIds.indexOf(i + 1)) - 1);
                }
                DiscountData temp = datas.get(i);
                String temp_s = listSource.get(i);
                datas.set(i, datas.get(i+1));
                listSource.set(i, listSource.get(i+1));
                datas.set(i+1, temp);
                listSource.set(i+1, temp_s);
                i++;
            }
        }
        return i;
    }

    /**
     * @param nameContains Ignore cases, no filter if empty
     * @param shopContains Ignore cases, no filter if empty
     * @param discountGreaterThan Not strictly, no filter if 0
     * @param discountLessThan Not strictly, no filter if 100
     * @param after Not strictly, no filter if null
     * @param before Not strictly, no filter if null
     * @return Returns the list of the valid elements of listSource, and adds the ids of the valid elements to filterIds
     */
    public ArrayList<String> filter(String nameContains, String shopContains, int discountGreaterThan, int discountLessThan, Date after, Date before) {
        ArrayList<String> filtered = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            if (!nameContains.isEmpty() && !datas.get(i).name.toLowerCase().contains(nameContains.toLowerCase()))
                continue;
            if (!shopContains.isEmpty() && !datas.get(i).shop.toLowerCase().contains(shopContains.toLowerCase()))
                continue;
            if (discountGreaterThan != 0 && datas.get(i).discountPercent < discountGreaterThan)
                continue;
            if (discountLessThan != 100 && datas.get(i).discountPercent > discountLessThan)
                continue;
            if (after != null && datas.get(i).expirationDate.compareTo(after) <= 0)
                continue;
            if (before != null && datas.get(i).expirationDate.compareTo(before) >= 0)
                continue;

            filterIds.add(i);
            filtered.add(listSource.get(i));
        }
        return filtered;
    }

    public void clearFilter() {
        filterIds.clear();
    }

    public void delete(int id) {
        datas.remove(id);
        listSource.remove(id);
        if (filterIds.contains(id)) {
            filterIds.remove(filterIds.indexOf(id));
        }
    }

    public int count() {
        return datas.size();
    }

    // parse date in yyyy/mm/dd format
    public static Date parseDate(String date) {
        String[] a = date.split("/");
        return new Date(Integer.parseInt(a[0]) - 1900, Integer.parseInt(a[1]) - 1, Integer.parseInt(a[2]));
    }

    public String[] toArr() {
        String[] a = new String[listSource.size()];
        for (int i = 0; i < listSource.size(); i++) {
            a[i] = listSource.get(i);
        }
        return a;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        /*
            YAML format:
            name: %s\n
            shop: %s\n
            discountSize: %d\n
            expirationDate: %d/%d/%d\n
            \n
            name:%s\n
            ...
         */
        for (DiscountData data : datas) {
            res.append(String.format("name: %s\nshop: %s\ndiscountSize: %d\nexpirationDate: %d/%d/%d\n\n",
                    data.name, data.shop, data.discountPercent,
                    data.expirationDate.getYear()+1900, data.expirationDate.getMonth()+1, data.expirationDate.getDay()));
        }
        return res.toString();
    }
}
