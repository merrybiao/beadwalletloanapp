#------------------自动部署脚本------------------

echo "1. 进入git/svn项目目录===================="
cd /root/workspace/

# echo "2. git/svn切换分支到v1.0===================="
# git checkout v1.0
echo "2. svn checkout分支项目到本地"
# svn checkout http://106.14.61.168:82/svn/project/WaterElephant/Project-new/beadwalletloanapp/trunk/ver-1.0.0/ beadwalletloanapp --username huangxiao

# echo "3. git fetch===================="
# git fetch
echo "3. 进入项目目录，svn update或git pull===================="
cd beadwalletloanapp/
svn update

echo "5. 编译并跳过单元测试，并只编译test环境下的配置===================="
mvn clean package -Dmaven.test.skip=true -Ptest

echo "6. 删除旧的beadwalletloanapp.war===================="
rm -f /usr/local/tomcat-loanapp/webapps/beadwalletloanapp.war


echo "7. 拷贝编译出来的war包到tomcat下-ROOT.war===================="
cp /root/workspace/beadwalletloanapp/target/beadwalletloanapp.war /usr/local/tomcat-loanapp/webapps/beadwalletloanapp.war


echo "8. 删除tomcat下旧的ROOT文件夹===================="
rm -rf /usr/local/tomcat-loanapp/webapps/beadwalletloanapp/



echo "9. 关闭tomcat===================="
/usr/local/tomcat-loanapp/bin/shutdown.sh


echo "10. Sleep 10s===================="
for i in {1..10}
do
	echo $i"s"
	sleep 1s
done


echo "11. 启动tomcat===================="
/usr/local/tomcat-loanapp/bin/startup.sh


echo "12. tail -f /usr/local/tomcat-loanapp/logs/catalina.out===================="
tail -f /usr/local/tomcat-loanapp/logs/catalina.out
