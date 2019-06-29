package com.bo.spring.utils;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;//EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/*
 * 
 * 
 * 基于用户的协同过滤
 * 
 * 利用apache mahout开源项目
 * 
 * 通过基于用户的协同过滤算法，来给用户推荐商品
 * 
 */
public class UserBaseCFMain {

	final static int NEIGHBORHOOD_NUM = 3;// 邻居数目
	final static int RECOMMENDER_NUM = 7;// 推荐物品数目

	public static void main(String[] args) throws Exception {
		String file = "item.csv";
		// 从文件加载数据
		DataModel model = new FileDataModel(new File(file));// FileDataModel要求文件数据存储格式（必须用“，”分割）：userID,itemID[,preference[,timestamp]]
		// 指定用户相似度计算方法,欧式距离相似性
		UserSimilarity user = new PearsonCorrelationSimilarity(model);
		// 指定用户邻居数量
		NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(NEIGHBORHOOD_NUM, user, model);
		// 构建基于用户的推荐系统
		Recommender r = new GenericUserBasedRecommender(model, neighbor, user);
		// 得到所有用户的ID集合
		LongPrimitiveIterator iter = model.getUserIDs();

		while (iter.hasNext()) {
			long uid = iter.nextLong();
			// 获取推荐结果
			List<RecommendedItem> list = r.recommend(uid, RECOMMENDER_NUM);
			System.out.printf("uid:%s", uid);
			// 遍历推荐结果,输出
			for (RecommendedItem ritem : list) {
				System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
			}
			System.out.println();
		}
	}

}