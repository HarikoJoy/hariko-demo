package com.hariko.alg.code;

import java.io.*;
import java.util.*;

public class HuffmanCode {

    public static void main(String[] args) {
        String byteStr = "i like like like java do you like a java";
        byte[] byteArray = byteStr.getBytes();
        byte[] zipByteArray = huffmanZip(byteArray);

        byte[] unzipByteArray = huffmanUnzip(zipByteArray, huffmanByteCode);

        String destStr = new String(unzipByteArray);
        System.out.println("解压缩的字符串为:" + destStr);
    }

    private static void unzipFile(String srcFile, String destFile) throws Exception{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(srcFile));
        byte[] zipByteArray = (byte[]) ois.readObject();
        Map<Byte, String> huffmanByteArray = (Map<Byte, String>) ois.readObject();
        ois.close();

        byte[] unzipByteArray = huffmanUnzip(zipByteArray, huffmanByteArray);
        FileOutputStream fos = new FileOutputStream(new File(destFile));
        fos.write(unzipByteArray);
        fos.close();
    }

    private static void zipFile(String srcFile, String destFile) throws Exception{
        FileInputStream fis = new FileInputStream(new File(srcFile));
        byte[] byteFile = new byte[fis.available()];
        fis.read(byteFile);
        fis.close();

        byte[] huffmanByteArray = huffmanZip(byteFile);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(destFile)));
        oos.writeObject(huffmanByteArray);
        oos.writeObject(huffmanByteCode);
        oos.close();
    }

    private static byte[] huffmanUnzip(byte[] byteArray, Map<Byte, String> huffmanCodeMap){
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < byteArray.length; i++){
            boolean flag = i == byteArray.length - 1;
            stringBuilder.append(getBit8String(!flag, byteArray[i]));
        }

        System.out.println("还原进制一位变为8位:" + stringBuilder.toString());

        //huffman编码表进行反转
        Map<String, Byte> huffmanRevertMap = new HashMap<>();
        for(byte key : huffmanCodeMap.keySet()){
            String value = huffmanCodeMap.get(key);

            huffmanRevertMap.put(huffmanCodeMap.get(key), key);
        }
        System.out.println("反转huffman编码表为:" + huffmanRevertMap);

        List<Byte> destByteList = new ArrayList<>();
        for(int i = 0; i < stringBuilder.length(); /**这里不需要i++,因为后面直接加的count**/){
            int count = 1;
            boolean flag = true;
            Byte bb = null;

            while (flag){
                //i不动,让count移动,指定匹配到一个字符
                String key = stringBuilder.substring(i, i + count);
                bb = huffmanRevertMap.get(key);

                if(null == bb){
                    count++;
                }else{
                    //匹配到,退出循环
                    flag = false;
                }
            }

            destByteList.add(bb);
            i += count;
        }

        byte[] unzipByteArray = new byte[destByteList.size()];
        for(int i = 0; i < destByteList.size(); i++){
            unzipByteArray[i] = destByteList.get(i);
        }
        System.out.println("所有的单个byte的字符数组为:" + Arrays.toString(unzipByteArray));
        return unzipByteArray;
    }

    /**
     *
     * @param flag 是否需要补位
     * @param b    需要解码的数组
     * @return
     */
    private static String getBit8String(boolean flag, byte b){

        if(flag){
            //如果是正数,需要补高位
            int temp = b | 256; // 1 0000 0000  | 0000 0001  =  1 0000 0001
            String str = Integer.toBinaryString(temp);
            return str.substring(str.length() - 8);
        }else{
            String str = Integer.toBinaryString(b);
            return str.length() > 8 ? str.substring(str.length() - 8) : str;
        }

    }

    private static byte[] huffmanZip(byte[] byteArray){
        System.out.println("原始数组的长度为:" + byteArray.length);

        List<Node> nodeList = getNodeList(byteArray);
        System.out.println("转换结点列表为:" + nodeList);
        //创建huffman树
        Node root = createHuffmanTree(nodeList);
        //前序遍历
        System.out.println("前序遍历结果为:");
        root.preOrder();

        System.out.println("生成的huffman编码表是:" + getHuffmanCode(root));

        byte[] zipByteArray = zip(byteArray, getHuffmanCode(root));
        System.out.println("压缩后的字节数组:" + Arrays.toString(zipByteArray));

        return zipByteArray;
    }

    /**
     *
     * @param srcByteArray 原始字符串对应的字节数组
     * @param huffmanCodeMap huffman编码表
     * @return 压缩后的字节数组
     */
    private static byte[] zip(byte[] srcByteArray, Map<Byte, String> huffmanCodeMap){
        StringBuilder stringBuilder = new StringBuilder();

        for(byte b : srcByteArray){
            stringBuilder.append(huffmanCodeMap.get(b));
        }

        System.out.println("按照编码表转换成的二进制串为:" + stringBuilder.toString());

        int yushu = stringBuilder.length() % 8;
        int shang =  stringBuilder.length() / 8;
        int length = yushu == 0 ? shang : shang + 1; // int length = (stringBuilder.length() + 7) / 8

        byte[] destByteArray = new byte[length];
        int index = 0;
        for(int i = 0; i < stringBuilder.length(); i += 8){
            String byteStr;
            if(i + 8 > stringBuilder.length()){
                byteStr = stringBuilder.substring(i, stringBuilder.length());
            }else {
                byteStr = stringBuilder.substring(i, i + 8);
            }
            destByteArray[index++] = (byte)Integer.parseInt(byteStr, 2);
        }

        return destByteArray;
    }

    //1.将huffman编码表放在map中,Map<Byte, String>  32->01 97->100
    private static Map<Byte, String> huffmanByteCode = new HashMap<>();
    //2.生成过程中需要不断的拼接路徑,定义一个StringBuilder,存储某个叶子结点的路徑
    private static StringBuilder stringBuilder = new StringBuilder();

    private static Map<Byte, String> getHuffmanCode(Node root){
        if(null == root){
            return null;
        }

        getHuffmanCode(root.left, "0", stringBuilder);
        getHuffmanCode(root.right, "1", stringBuilder);

        return huffmanByteCode;
    }

    /**
     * {32=01, 97=100, 100=11000, 117=11001, 101=1110, 118=11011, 105=101, 121=11010, 106=0010, 107=1111, 108=000, 111=0011}
     * @param node 传入结点
     * @param code 路徑: 左子结点是0,右子结点是1
     * @param stringBuilder 用于拼接路径
     */
    private static void getHuffmanCode(Node node, String code, StringBuilder stringBuilder){
        StringBuilder innerStringBuilder = new StringBuilder(stringBuilder);

        innerStringBuilder.append(code);
        if(null != node){

            //判断是不是叶子结点
            if(null == node.data) {
                getHuffmanCode(node.left, "0", (innerStringBuilder));

                getHuffmanCode(node.right, "1", (innerStringBuilder));
            }else{
                huffmanByteCode.put(node.data, innerStringBuilder.toString());
                return;
            }
        }
    }

    private static List<Node> getNodeList(byte[] byteArray){
        List<Node> nodeList = new ArrayList<>();

        //统计每个byte出现的次数 -> map[key, value]
        Map<Byte, Integer> map = new HashMap<>();
        for(byte b : byteArray){
            Integer count = map.get(b);
            if(count == null){
                map.put(b, 1);
            }else{
                map.put(b, count + 1);
            }
        }

        //把每个map的元素转成一个Node,并加入nodeList中
        for(byte key : map.keySet()){
            nodeList.add(new Node(key, map.get(key)));
        }

        return nodeList;
    }

    public static Node createHuffmanTree(List<Node> nodeList){
        while (nodeList.size() > 1){
            Collections.sort(nodeList);
            handleList(nodeList);
        }

        //list中只有一个元素了,就是根结点
        return nodeList.get(0);
    }

    public static List<Node> handleList(List<Node> list){
        //1.取出权值最小的两棵树
        Node left = list.get(0);
        Node right = list.get(1);

        Node parent = new Node(null,left.weight + right.weight);
        parent.left = left;
        parent.right = right;

        list.remove(left);
        list.remove(right);
        list.add(parent);

        return list;
    }
}

class Node implements Comparable{
    Byte data; //存放数据(字符)本身,比如'a' => 97  ' ' => 32
    int weight; //权值,表示字符出现的次数
    Node left;
    Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(Object o) {
        Node node = (Node)o;
        return this.weight - node.weight;
    }

    public void preOrder(){
        System.out.println(this);

        if(this.left != null){
            this.left.preOrder();
        }

        if(this.right != null){
            this.right.preOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }
}
