package perishing.constraint.treasure.chest.converter;

class LongStringConverter implements Converter<Long, String> {

    @Override
    public String to(Long aLong) {
        return aLong.toString();
    }

    @Override
    public Long from(String s) {
        return Long.valueOf(s);
    }

}
