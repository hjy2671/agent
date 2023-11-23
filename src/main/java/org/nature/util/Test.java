package org.nature.util;

import org.nature.util.json.JsonParser;
import org.nature.util.json.NormalType;
import org.nature.util.json.TypeFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author hjy
 * 2023/8/19
 */
public class Test {
    public static void main(String[] args) throws NoSuchFieldException {
        String target = "[{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"电影《食神》于1996年12月21日上映。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，菜品[禾花雀]因为厨师太丑得了零分。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，唐牛背叛了食神史提芬周。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，史提芬周的餐馆里用了坏掉的牛肉。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，唐牛成为了新食神。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，撒尿牛丸的第一位顾客是厌食症患者\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，撒尿牛丸被用来打乒乓球。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，周星驰饰演的食神史提芬周靠撒尿牛丸翻身。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，火鸡姐因为保护食神旗被毁容。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，火鸡姐是食神史提芬周的粉丝。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，火鸡姐为食神史提芬周档了一刀。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，食神史提芬周成为了少林弟子。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，火鸡姐曾给史提芬周做了一碗叉烧饭。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，史提芬周与唐牛PK做佛跳墙。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，唐牛去的中国厨艺训练学院，其实是少林寺厨房。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，火鸡姐救了周星驰饰演的食神史提芬周。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，食神比赛当晚出现了九星连珠。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，火鸡姐非常喜欢史提芬周。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，食神史提芬周被徒弟唐牛当众击败。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，史提芬周做出的撒尿牛丸很有弹性。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，史提芬周误入了少林寺。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，史提芬周非常有商业头脑。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，史提芬周靠撒尿牛丸重新成为食神。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，唐牛曾经是少林寺学徒。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，史提芬周是全港知名的食神，在饮食界首屈一指。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，使用隔夜米饭来炒米饭是最基本的常识。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，史提芬制作甜品[彩虹鲜花拔丝]是麦芽糖、鲜花瓣制作的。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，在《香港至尊名厨大赛》中史提芬将四大名厨的菜通通打成0分。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，史蒂芬凭撒尿牛丸入围香港饮食奇才。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，火鸡姐摊位下贴满了史蒂芬的照片。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，”好折凳”被誉为七种武器之首。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，食神制作的又烧饭，起名是[黠然销魂饭]。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，方丈讨厌别人在背后说他坏话。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，只要用心，人人都可以是食神。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，[赔然销魂饭]吃了会流泪，是因为放了洋葱 \"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，史提芬周在做菜时，使出[屠龙斩]。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，火鸡姐最终变得很漂亮。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，火鸡姐绰号[双刀火鸡]。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"A\",\"q\":\"《食神》电影中，少林寺方丈，法号为梦遗。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，皇帝炒饭得到了食神周星驰的肯定，拿到满分。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，火鸡做出了好吃的撒尿牛丸。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，撒尿牛丸的第一位顾客是唐牛。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，唐牛比赛做的佛跳墙用了七七四十九小时。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，参加食神比赛的人都拿了满分。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，周星驰饰演的食神给所有参赛者都打了满分。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，史提芬周参加食神比赛迟到了。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，，史提芬周与唐牛PK做皇帝炒饭。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，史提芬周一直都是食神。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，火鸡姐曾在中国厨艺技术学院学习。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，史提芬周的徒弟唐牛喜欢火鸡姐。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，火鸡姐最终去了少林寺。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，唐牛的拿手菜是撒尿牛丸。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，火鸡姐卖给史蒂芬是一碗叉烧饭。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，卖出第一碗[撒尿牛丸]的价格是1元。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，史蒂芬去少林寺的厨房学习仅用了2个月。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，《香港至尊名厨大赛》比赛地点在少林寺。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，最终史提芬周靠咸鱼料理赢得了比赛。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，史提芬周最后在少林寺做和尚，法号星星。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，史提芬周加入了少林寺十八铜人。\"}\n" +
                "{\"a\":[\"对\",\"错\"],\"ans\":\"B\",\"q\":\"《食神》电影中，火鸡姐最终和方丈在一起了。\"}]";
        String test = "[\"a\",\"a\",\"a\"]";
        target = target.replace("\n", ",");
        System.out.println(target);
        final XIAN[] xians = (XIAN[]) new JsonParser(target, XIAN[].class, new TypeFactory()).parse();
        final List<XIAN> xians1 = new JsonParser(target, List.class, new TypeFactory()).parseList(XIAN.class);
        int i = 1;
        for (XIAN xian : xians1) {
            System.out.println(i++ + "、" + xian);
        }
        System.out.println(XIAN[][].class.getComponentType().getComponentType().getDeclaredField("ans").getName());
    }
}

class XIAN {
    String[] a;//对错
    String ans;//答案
    String q;//题目

    public String[] getA() {
        return a;
    }

    public void setA(String[] a) {
        this.a = a;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    @Override
    public String toString() {
        return q + "(" + ans + ")。\n"
                +"A、正确 B、错误";
    }
}