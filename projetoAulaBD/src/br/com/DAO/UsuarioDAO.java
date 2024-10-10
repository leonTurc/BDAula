package br.com.DAO;


import br.com.dto.UsuarioDto;
import java.sql.*;
import javax.swing.JOptionPane;
import br.com.bios.*;
import jdk.nashorn.internal.scripts.JO;

public class UsuarioDAO {
    Connection conexao= null;
    PreparedStatement pst= null;
    ResultSet rs= null;
    
    public void limpar() {
        telaUsuarios.txtNome.setText(null);
        telaUsuarios.txtLog.setText(null);
        telaUsuarios.txtPass.setText(null);

    }
    
    
    public void logar(UsuarioDto objDto){
        String sql= "select * from tb_usuarios where login = ? and senha = ?";
        conexao= ConexaoDAO.connector();
        try{
            pst = conexao.prepareStatement(sql);
            pst.setString(1, telaLogin.txtUsuario.getText());
            pst.setString(2, telaLogin.txtSenha.getText());
            
            rs= pst.executeQuery();
            
            if(rs.next()){
                TelaPrincipal pri= new TelaPrincipal();
                pri.setVisible(true);
            } else{
                JOptionPane.showMessageDialog(null, "login e/ou senha invalidos");
            }
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Logar "+e);
        }
    }
    
    
    public void addUsu(UsuarioDto objDto){
        
        String sql = "insert into tb_usuarios(usuario, login, senha, id) values(?, ?, ?, ?)";
        conexao= ConexaoDAO.connector();
        
        try{
            
            pst= conexao.prepareStatement(sql);
            pst.setString(1, objDto.getNomeUsu());
            pst.setString(2, objDto.getLoginUsu());
            pst.setString(3, objDto.getSenhaUsu());
            pst.setInt(4, objDto.getIdUsu());
            
           int add =  pst.executeUpdate();
           
           if(add > 0){
               JOptionPane.showMessageDialog(null, "adicionado com sucesso");
           }
           
            pst.close();
            
            
        }catch(Exception e){
            if(e.getMessage().contains("'tb_usuarios.PRIMARY'")){
                JOptionPane.showMessageDialog(null, "ID ja em uso.");
            } else if (e.getMessage().contains("'tb_usuarios.login_UNIQUE'")){
                JOptionPane.showMessageDialog(null, "login ja em uso.");
            } else{
                JOptionPane.showMessageDialog(null, "metodo addUsu: "+e.getMessage());
            }
        }
        
        
    }
    
    public void pesquisar(UsuarioDto objDto){
        
        String sql = "select * from tb_usuarios where id = ?";
        conexao= ConexaoDAO.connector();
        try {
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objDto.getIdUsu());
            rs = pst.executeQuery();

            if (rs.next()) {
                telaUsuarios.txtNome.setText(rs.getString(1));
                telaUsuarios.txtLog.setText(rs.getString(2));
                telaUsuarios.txtPass.setText(rs.getString(3));
                conexao.close();
                
            } else {
                JOptionPane.showMessageDialog(null,"não cadastrado");
                limpar();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "metodo pesquisar " + e);
        }
        
    }
    
    public void deletar(UsuarioDto objDto){
        String sql= "delete from tb_usuarios where id= ?";
        conexao= ConexaoDAO.connector();
        
        try{
            pst= conexao.prepareStatement(sql);
            pst.setInt(1, objDto.getIdUsu());
            
            int go= pst.executeUpdate();
            
            if (go>0){
                JOptionPane.showMessageDialog(null, "deletado com sucesso");
            }
            
        } catch (Exception e){
            
        }
    }
    
    public void atualizar(UsuarioDto dto){
        int res= JOptionPane.showConfirmDialog(null, "Quer mesmo alterar este usuario?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if(res == JOptionPane.YES_OPTION){
            String sql= "update tb_usuarios set usuario = ?, login= ?, senha= ? where id= ?";
            conexao= ConexaoDAO.connector();
            
            try{
                pst= conexao.prepareStatement(sql);
                pst.setString(1, dto.getNomeUsu());
                pst.setString(2, dto.getLoginUsu());
                pst.setString(3, dto.getSenhaUsu());
                pst.setInt(4, dto.getIdUsu());
                
                int result= pst.executeUpdate();
                
                if(result > 0){
                JOptionPane.showMessageDialog(null, "Alterado com sucesso");
            } else {
                    JOptionPane.showMessageDialog(null, "erro ao alterar");
                    }
                
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "metodo alterar: "+e);
                
            }
    }
    
    
}
}
