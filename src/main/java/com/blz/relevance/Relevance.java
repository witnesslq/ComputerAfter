package com.blz.relevance;

import java.io.*;

public class Relevance {
	// n行3列
	double[][] matrix = null;
	final static int m = 3;
	static int n = 0;

	double[][] X_0 = null;
	double[][] X_1 = null;
	double[][] X_2 = null;
	double[][] X_3 = null;
	double[][] a1_0 = null;
	double[][] a2_0 = null;
	double[][] a3_0 = null;
	double[] x0 = new double[3];

	/**
	 * 初始化矩阵数据
	 *
	 * @throws IOException
	 */
	public void initData() throws IOException {
		// 确定矩阵行数
		BufferedReader in = new BufferedReader(new FileReader(new File("/home/hadoop/tmpdata/computerdata2.txt")));
		int num = 0;
		while (in.readLine() != null) {
			num++;
		}
		n = num;
		in.close();
		//=========================================
		matrix = new double[num + 1][3];
		BufferedReader in2 = new BufferedReader(new FileReader(new File("/home/hadoop/tmpdata/computerdata2.txt")));
		String line2 = null;
		int num2 = 0;
		while ((line2 = in2.readLine()) != null) {
			String[] lines = line2.split("\t");
			matrix[num2][0] = Double.parseDouble(lines[1]);
			matrix[num2][1] = Double.parseDouble(lines[2]);
			matrix[num2][2] = Double.parseDouble(lines[3]);
			++num2;
		}
		in2.close();
		//=========================================
		X_0 = new double[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				X_0[i][j] = 0;
			}
		}

		X_2 = new double[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				X_2[i][j] = 0;
			}
		}

		X_3 = new double[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				X_3[i][j] = 0;
			}
		}

		//=========================================
		a1_0 = new double[n][1];
		for (int i = 0; i < n; i++) {
			a1_0[i][0] = matrix[i][0];
		}

		a2_0 = new double[n][1];
		for (int i = 0; i < n; i++) {
			a2_0[i][0] = matrix[i][1];
		}

		a3_0 = new double[n][1];
		for (int i = 0; i < n; i++) {
			a3_0[i][0] = matrix[i][2];
		}

		//=========================================
		X_1 = new double[n][3];
		for (int i = 0; i < n; i++) {
			X_1[i][0] = a1_0[i][0];
			X_1[i][1] = a1_0[i][0];
			X_1[i][2] = a1_0[i][0];
		}
		//=========================================
		x0[0] = getMax(a1_0);
		x0[1] = getMax(a2_0);
		x0[2] = getMax(a3_0);
		//=========================================
	}

	public void count() throws IOException {
		int i = 0;
		while (i != m) {
			for (int j = 0; j < n; j++) {
				X_2[j][i] = Math.abs(X_1[j][i] - x0[i]);
			}
			i += 1;
		}

		//确认偏差
		//=========================================
		double error_min = getMin(X_2);
		double error_max = getMax(X_2);

		//=========================================
		i = 0;
		double p = 0.5;
		while (i != m) {
			for (int j = 0; j < n; j++) {
				X_3[j][i] = (error_min + p * error_max) / (X_2[j][i] + p * error_max);
			}
			i = i + 1;
		}
		//=========================================
		double[] a = new double[n];
		for (int j = 0; j < n; j++) {
			a[j] = 0;
		}

		//=========================================
		for (int j = 0; j < n; j++) {
			for (int i1 = 0; i1 < m; i1++) {
				a[j] = a[j] + X_3[j][i1];
			}
			a[j] = a[j] / m;
		}


		BufferedReader in = new BufferedReader(new FileReader(new File("/home/hadoop/tmpdata/computerdata2.txt")));
		BufferedWriter out = new BufferedWriter(new FileWriter(new File("/home/hadoop/tmpdata/computerrel.txt")));

		int num = 0;
		while (in.readLine() != null) {
			++num;
		}
		in.close();

		BufferedReader in2 = new BufferedReader(new FileReader(new File("/home/hadoop/tmpdata/computerdata2.txt")));
		String line = null;
		String[] name = new String[num];
		int num2 = 0;
		while ((line = in2.readLine()) != null) {
			name[num2] = line.split("\t")[0];
			++num2;
		}

		for (int k = 0; k < a.length; k++) {
			if (k == 0) {
				continue;
			}
			out.write(name[k] + "\t" + k + "\t" + a[k]);
			out.newLine();
			out.flush();
		}
		out.close();
	}


	/**
	 * 获取二维数组最大值
	 *
	 * @param array
	 * @return
	 */
	public double getMax(double[][] array) {
		double max = array[0][0];
		int row = 0, col = 0;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (max < array[i][j]) {
					max = array[i][j];
					row = i;
					col = j;
				}
			}
		}
		return array[row][col];
	}

	public double getMin(double[][] array) {
		double min = array[0][0];
		int row = 0, col = 0;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (min > array[i][j]) {
					min = array[i][j];
					row = i;
					col = j;
				}
			}
		}
		return array[row][col];
	}

	public void start() throws IOException {
		Relevance r = new Relevance();
		r.initData();
		r.count();
	}

}
