package nc.mairie.visusal.metier;

public class BulletinElement {

	public String noRubr="";
	public String liRubr="";
	public String nombre="";
	public String taux="";
	public String mtBases="";
	public String montant="";
	public String signe="P"; //P=A PAYER, R= ARETENIR
	
	public String getLiRubr() {
		return liRubr;
	}
	public void setLiRubr(String liRubr) {
		this.liRubr = liRubr;
	}
	public String getMontant() {
		return montant;
	}
	public void setMontant(String montant) {
		this.montant = montant;
	}
	public String getMtBases() {
		return mtBases;
	}
	public void setMtBases(String mtBases) {
		this.mtBases = mtBases;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNoRubr() {
		return noRubr;
	}
	public void setNoRubr(String noRubr) {
		this.noRubr = noRubr;
	}
	public String getSigne() {
		return signe;
	}
	public void setSigne(String signe) {
		this.signe = signe;
	}
	public String getTaux() {
		return taux;
	}
	public void setTaux(String taux) {
		this.taux = taux;
	}
	
	
}
