package com.mondari;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

public class CodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("limondar");
        gc.setFileOverride(true);
        gc.setOpen(false);
        // 1-> Entity设置
        gc.setIdType(IdType.AUTO);  // 设置主键类型
        gc.setSwagger2(true);   // 开启实体属性 Swagger2 注解
//        gc.setActiveRecord(true);
        // 2-> Mapper.xml设置
        gc.setBaseColumnList(true); // 开启通用查询结果列
        gc.setBaseResultMap(true);  // 开启通用查询映射结果
        // 3-> Mapper.java设置
//        gc.setMapperName("%sDao");  // 设置自己的命名规则，如果对Mapper.java的名称不满意的话
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://centos-vm:3306/db_example" +
                "?useUnicode=true&characterEncoding=UTF8&useSSL=false&serverTimezone=GMT%2B8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("toor");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.limondar");
        // pc.setModuleName(scanner("模块名"));
        pc.setMapper("dao");    // 设置 Mapper.java 包名，默认是放在 mapper 包中
        mpg.setPackageInfo(pc);

        // 自定义配置
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                // to do nothing
//            }
//        };
//
//        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
//
//        // 自定义输出配置
//        List<FileOutConfig> focList = new ArrayList<>();
//        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig(templatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
//                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });
//        /*
//        cfg.setFileCreate(new IFileCreate() {
//            @Override
//            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
//                // 判断自定义文件夹是否需要创建
//                checkDir("调用默认方法创建的目录");
//                return false;
//            }
//        });
//        */
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);
//
//        // 配置模板
//        TemplateConfig templateConfig = new TemplateConfig();
//
//        // 配置自定义输出模板
//        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
//        // templateConfig.setEntity("templates/entity2.java");
//        // templateConfig.setService();
//        // templateConfig.setController();
//
//        templateConfig.setXml(null);
//        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        // strategy.setTablePrefix(pc.getModuleName() + "_");   // 设置表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        // -> 设置Entity
        strategy.setEntityLombokModel(true);
//        strategy.setVersionFieldName("");   // 设置乐观锁字段
//        strategy.setLogicDeleteFieldName("");   // 设置逻辑删除字段（需要注意逻辑删除和唯一主键会出现冲突的问题，解决问题是加入一个delete_token字段，参考https://www.jianshu.com/p/f37281576585）
        // -> 设置Controller
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true); // 设置驼峰转连字符：@RequestMapping("/managerUserActionHistory") -> @RequestMapping("/manager-user-action-history")

        // 设置 Entity、Mapper、Mapper.xml、Service、ServiceImpl 的父类
//        strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
//        设置Entity父类公共字段
//        strategy.setSuperEntityColumns("id");

        mpg.setStrategy(strategy);

        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }
}
