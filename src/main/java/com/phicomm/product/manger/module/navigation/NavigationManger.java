package com.phicomm.product.manger.module.navigation;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.phicomm.product.manger.exception.DataFormatException;
import com.phicomm.product.manger.model.navigation.NavigationModel;
import com.phicomm.product.manger.utils.ExceptionUtil;
import com.phicomm.product.manger.utils.HttpUtil;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 导航栏数据填充
 * Created by yufei.liu on 2017/6/5.
 */
@Component
public class NavigationManger {

    private static final String NAVIGATION_CONFIG_FILE = "navigation.xml";

    private static final Logger logger = Logger.getLogger(NavigationManger.class);

    private Map<String, NavigationModel> navigationModelMap;

    public NavigationManger() throws DataFormatException {
        navigationModelMap = Maps.newHashMap();
        readConfigurationFile();
        print();
    }

    /**
     * 解析配置文件
     */
    private void readConfigurationFile() throws DataFormatException {
        URL permissionConfigFile = Resources.getResource(NAVIGATION_CONFIG_FILE);
        SAXReader reader = new SAXReader();
        Document document;
        File configFile = new File(permissionConfigFile.getFile());
        try {
            document = reader.read(configFile);
        } catch (DocumentException e) {
            logger.error(ExceptionUtil.getErrorMessage(e));
            return;
        }
        parse(document);
    }

    /**
     * 具体解析
     */
    private void parse(Document document) throws DataFormatException {
        Element root = document.getRootElement();
        Iterator navigationIterator = root.elementIterator("navigation");
        while (navigationIterator.hasNext()) {
            Element navigation = (Element) navigationIterator.next();
            String id = navigation.element("id").getTextTrim();
            Element titleElement = navigation.element("title");
            String titleName = titleElement.element("title-name").getTextTrim();
            String titleDescription = titleElement.element("title-description").getTextTrim();
            Element directoryElement = navigation.element("directory");
            String directoryIcon = directoryElement.element("icon").getTextTrim();
            String directoryName = directoryElement.element("name").getTextTrim();
            String directoryUrl = directoryElement.element("url").getTextTrim();
            Element subDirectoriesElement = directoryElement.element("sub-directories");
            List<String> subDirectoriesNames = Lists.newArrayList();
            Iterator subDirectoriesIterator = subDirectoriesElement.elementIterator("name");
            while (subDirectoriesIterator.hasNext()) {
                Element subDirectoriesNameElement = (Element) subDirectoriesIterator.next();
                String name = subDirectoriesNameElement.getTextTrim();
                subDirectoriesNames.add(name);
            }
            if (!navigationModelMap.containsKey(id)) {
                navigationModelMap.put(id, new NavigationModel(id,
                        titleName,
                        titleDescription,
                        directoryIcon,
                        directoryName,
                        directoryUrl,
                        subDirectoriesNames));
            } else {
                throw new DataFormatException();
            }
        }
    }

    /**
     * 打印结果
     */
    private void print() {
        Set<String> keys = navigationModelMap.keySet();
        keys.forEach((key) -> logger.info(navigationModelMap.get(key)));
    }

    /**
     * 导航栏
     *
     * @param id id
     * @return 导航栏数据
     */
    public NavigationModel getNavigationModel(String id) {
        NavigationModel navigationModel = navigationModelMap.get(id);
        String jsonString = JSON.toJSONString(navigationModel);
        NavigationModel temp = JSON.parseObject(jsonString, NavigationModel.class);
        String url = temp.getDirectoryUrl().replace("$baseUrl", HttpUtil.getBaseUrl());
        temp.setDirectoryUrl(url);
        return temp;
    }

}
