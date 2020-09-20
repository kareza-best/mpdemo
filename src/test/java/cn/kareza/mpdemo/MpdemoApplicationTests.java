package cn.kareza.mpdemo;

import cn.hutool.core.date.DateUtil;
import cn.kareza.mpdemo.entity.Trending;
import cn.kareza.mpdemo.mapper.TrendingMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class MpdemoApplicationTests {

    @Autowired
    private TrendingMapper trendingMapper;

    // 查询trending表所有数据
    @Test
    public void findAll() {
        List<Trending> trending = trendingMapper.selectList(null);
    }

    // 向trending表添加一条数据
    @Test
    public void addTrending() {
        Trending trending = trendingMapper.selectById(1307345304936275970L);
        trending.setId(null);
        trendingMapper.insert(trending);
    }

    // 根据id修改trending表中的一条数据
    @Test
    public void updateTrending() {

        Trending trending = new Trending();

        trending.setId(1307298681342492674L);
        trending.setTopRanking(45);
        trending.setStartDate(DateUtil.parseDate("2018-05-14"));
        trending.setStartTime(DateUtil.parseDateTime("2018-05-14 09:30:00"));
        trending.setEndTime(DateUtil.parseDateTime("2018-05-14 09:50:00"));

        trendingMapper.updateById(trending);
    }

    // 测试乐观锁
    @Test
    public void testOptimisticLocker() {

        // 根据id查询数据
        Trending trending = trendingMapper.selectById(1307298681342492674L);

        trending.setTopRanking(45);
        trending.setStartDate(DateUtil.parseDate("2018-05-14"));
        trending.setStartTime(DateUtil.parseDateTime("2018-05-14 09:30:00"));
        trending.setEndTime(DateUtil.parseDateTime("2018-05-14 09:50:00"));

        trendingMapper.updateById(trending);
    }

    // 多个id批量查询
    @Test
    public void testSelectBatchIds() {
        trendingMapper.selectBatchIds(Arrays.asList(1307146899937161217L, 1307149113942347777L));
    }

    // 条件查询
    @Test
    public void testSelectByMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("start_date", "2018-05-13");
        List<Trending> trendings = trendingMapper.selectByMap(map);

        trendings.forEach(System.out::println);
    }

    // 分页查询
    @Test
    public void testPage() {
        // 1.创建page对象
        // 传入两个参数：当前页 和 每页显示记录数
        Page<Trending> page = new Page<>(1, 2);
        // 调用mp分页查询的方法
        // 调用mp分页查询过程中，底层封装
        // 把分页所有数据封装到page对象里面
        trendingMapper.selectPage(page, null);

        // 通过page对象获取分页数据
        System.out.println(page.getCurrent());          // 当前页
        System.out.println(page.getRecords());          // 当前页数据list集合
        System.out.println(page.getSize());             // 每页显示记录数
        System.out.println(page.getTotal());            // 总记录数目
        System.out.println(page.getPages());            // 总页数

        System.out.println(page.hasNext());             // 是否有下一页
        System.out.println(page.hasPrevious());         // 是否有上一页
    }

    // 删除操作 物理删除/逻辑删除（取决于是否加载逻辑删除插件）
    @Test
    public void testDeleteById() {
        trendingMapper.deleteById(1307345304936275970L);
    }

    // 删除操作 批量删除
    @Test
    public void testDeleteBatchIds() {
        trendingMapper.deleteBatchIds(Arrays.asList(1307346401977499650L, 1307346634757152769L));
    }

    // mp实现复杂查询操作
    @Test
    public void testSelectQuery() {
        // 创建QueryWrapper对象
        QueryWrapper<Trending> wrapper = new QueryWrapper<>();

        // 通过QueryWrapper设置条件
        // ge、 gt、 le、 lt
        // 查询 topRanking <= 15 记录
        // 第一个参数字段名称，第二个参数设置值
        // 注意！字段名需为数据库中的名字
//        wrapper.le("top_ranking", 15);

        // eq、 ne
//        wrapper.eq("entries", "杨超越表情");
//        wrapper.ne("entries", "杨超越表情");

        // between
//        wrapper.between("top_ranking", 5, 15);

        // like
//        wrapper.like("entries", "杨超越");

        // orderByDesc
//        wrapper.orderByDesc("id");

        // last
//        wrapper.last("limit 1");

        // 指定要查询的列
        wrapper.select("start_date", "entries", "top_ranking");

        trendingMapper.selectList(wrapper);
    }

}
