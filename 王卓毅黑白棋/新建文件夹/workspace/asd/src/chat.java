import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.TextArea;

/**
 * ������ϢPanel��Panel�ϵ��ı��������ʾ�û�������Ϣ��
 */
public class chat extends Panel {
    //�����յ��ı���
    public TextArea chatLineArea = new TextArea("", 10, 10, TextArea.SCROLLBARS_VERTICAL_ONLY);

    public chat() {
        //�����Ű��ʽ
        setLayout(new BorderLayout());
       // ���ı�����ӵ�Panel����
        add(chatLineArea, BorderLayout.CENTER);
    }

}