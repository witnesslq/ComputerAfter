package com.blz.getresult.utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBC {
	Connection connection = null;
	private static final String driverName = "com.mysql.jdbc.Driver";
	private static final String userName = "root";
	private static final String passwd = "hadoop";
	private static final String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8";

	public List<ProList> getProList() throws SQLException {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(url, userName, passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		List<ProList> list = new ArrayList<>();
		String sql = "SELECT * FROM part";
		PreparedStatement statement = null;
		statement = this.connection.prepareStatement(sql);
		ResultSet set = statement.executeQuery();
		while (set.next()) {
			String proName = set.getString("pro");
			String cityName = set.getString("city");
			if (list.size() == 0) {
				List<String> cityList = new ArrayList<>();
				cityList.add(cityName);
				list.add(new ProList(proName, cityList));
			}

			for (int i = list.size()-1;i>=0;i--) {
				if (proName.equals(list.get(i).getPro())) {
					list.get(i).getCity().add(cityName);
					break;
				} else {
					List<String> cityList = new ArrayList<>();
					cityList.add(cityName);
					list.add(new ProList(proName, cityList));
					break;
				}
			}

		}

		return list;
	}
}
