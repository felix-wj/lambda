package cn.wangjie.lambda.util;

/**
 * @program: lambda
 * @description: 字符拼接
 * @author: WangJie
 * @create: 2018-08-09 16:05
 **/
public class StringCombiner {

    private final String prefix;
    private final String suffix;
    private final String delim;
    private final StringBuilder buIlder;

    public StringCombiner(String delim, String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.delim = delim;
        this.buIlder = new StringBuilder();
    }


    public StringCombiner add (String word) {
        if(!this.areAtStart()) {
            this.buIlder.append(delim);
        }
        this.buIlder.append(word);

        return this;
    }

    public StringCombiner merge (StringCombiner other) {
        if(!other.equals(this)) {
            if(!other.areAtStart() && !this.areAtStart()){
                other.buIlder.insert(0, this.delim);
            }
            this.buIlder.append(other.buIlder);
        }
        return this;
    }

    @Override
    public String toString() {
        return prefix + buIlder.toString() + suffix;
    }

    private boolean areAtStart() {
        return buIlder.length() == 0;
    }
}
