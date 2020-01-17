package com.italia.marxmind.bris.enm;

/**
 * 
 * @author mark italia
 * @since 03/06/2018
 * @version 1.0
 *
 */
public enum CaseKind {

	UNPAID_ACCOUNT(1, "UNPAID ACCOUNT"),
	BOUNDARY_CONFLICT(2,"BOUNDARY CONFLICT"),
	COLLECTION_OF_SUM_OF_MONEY(3, "COLLECTION OF SUM OF MONEY"),
	MORAL_DAMAGES(4, "MORAL DAMAGES"),
	ORAL_DEFAMATION(5, "ORAL DEFAMATION"),
	SEXUAL_HARASSMENT(6, "SEXUAL HARASSMENT"),
	LAND_CONFLICT(7, "LAND CONFLICT"),
	BOUNDARY_CONFLICT_THREAT(8, "BOUNDARY CONFLICT / THREAT"),
	PHYSICAL_INJURY_GRAVE_THREAT(9 , "PHYSICAL INJURY & GRAVE THREAT"),
	GRAVE_MISCONDUCT(10, "GRAVE MISCONDUCT"),
	LAND_CONFLICT_MORAL_DAMAGES(11, "LAND CONFLICT & MORAL DAMAGES"),
	HACKING_INCIDENT(12, "HACKING INCIDENT"),
	ADULTERY(13, "ADULTERY"),
	ILLEGAL_DEMOLITION_OF_PROPERTY(14, "ILLEGAL DEMOLITION OF PROPERTY"),
	NEGLIGENCE_OF_DUTY(15, "NEGLIGENCE OF DUTY"),
	CYBER_CRIME(16, "CYBER CRIME"),
	PUBLIC_DISTURBANCE(17, "PUBLIC DISTURBANCE"),
	TRESPASSING_BOUNDARY_CONFLICT(18, "TRESPASSING / BOUNDARY CONFLICT"),
	MAULING_RESULTING_TO_PHYSICAL_INJURY(19, "MAULING RESULTING TO PHYSICAL INJURY"),
	MAULING_RESULTING_TO_SLIGHT_PHYSICAL_INJURY(20, "MAULING RESULTING TO SLIGHT PHYSICAL INJURY"),
	SLIGHT_PHYSICAL_INJURY(21, "SLIGHT PHYSICAL INJURY"),
	GRAVE_THREAT(22, "GRAVE THREAT"),
	INTRIGUING_OF_HONOUR(23, "INTRIGUING OF HONOUR"),
	MAULING(24, "MAULING"),
	LIGHT_THREAT(25, "LIGHT THREATS (ART. 283)"),
	MORAL_DAMAGE_DEATH_THREAT(26, "MORAL DAMAGE & DEATH THREAT"),
	ILLEGAL_ENTRY(27, "ILLEGAL ENTRY"),
	PHYSICAL_INJURY(28, "PHYSICAL INJURY"),
	ALARMING_SCANDAL(29, "ALARMING SCANDAL"),
	THREAT(30, "THREAT"),
	INVITATION(31, "INVITATION"),
	
	
	DEATH_THREAT(32, "DEATH THREAT"),
	ACQUIRED_APPLIANCES(33, "NOT PAYING FOR APPLIANCES"),
	ACQUIRED_VEHICLES(34, "NOT PAYING FOR VEHICLES"),
	ACQUIRED_LAND(35, "NOT PAYING FOR LAND"),
	HOME_RENTING(36, "NOT PAYING FOR RENTING A HOUSE"),
	VEHICLE_RENTING(37, "NOT PAYING FOR RENTING A VEHICLE"),
	WRONG_INFO(38, "DELIVERING A WRONG NEWS OR INFORMATION TO THE PUBLIC"),
	FIGHTING_AND_INJURED(39, "FIGHTING AND INJURED"),
	RAPE(40,"RAPE CASE"),
	VALUABLE_THINGS(41,"STEALING OF VALUABLE THINGS"),
	ACCUSED_WRONG_PERSON(42, "VICTIMS OF WRONG ACCUSATIONS"),
	VALUABLE_FIRING(43, "BURNING OF VALUABLE THINGS AND LIKES"),
	BULLYING(44,"BULLYING"),
	LIFE_THREATENING(45,"THREATENING OF LIFE"),
	HUMAN_ASSAULT(46, "HUMAN ASSAULT"),
	ANIMAL_BITE(47,"ANIMAL BITE"),
	SEXUAL_ASSAULT(48,"SEXUAL ASSAULT"),
	VEHICULAR_ACCIDENT(49, "VEHICULAR ACCIDENT"),
	DEMAND_RETURN_PROPERTY(50, "DEMAND FOR RETURN OF PROPERTY"),
	OTHERS(51, "OTHER CASES"),
	
	
	UNLAWFUL_USE_PUBLICATION(52,"UNLAWFUL USE OF MEANS OF PUBLICATION AND UNLAWFUL UTTERANCES (ART. 154)"),
	ALARMS_SCANDAL(53,"ALARMS AND SCANDALS (ART. 155)"),
	FALSE_CERTI(54,"USING FALSE CERTIFICATE (ART. 175)"),
	FICTITIOUS_NAME(55,"USING FICTITIOUS NAMES AND CONCEALING TRUE NAMES (ART. 178)"),
	ILLEGAL_USE_UNIFORM(56,"ILLEGAL USE OF UNIFORMS AND INSIGNIAS (ART. 179)"),
	PHYSICAL_AFFRAY(57,"PHYSICAL INJURIES INFLICTED IN A TUMULTUOUS AFFRAY (ART. 252)"),
	CONSUMMATED_SUICIDE(58,"GIVING ASSISTANCE TO CONSUMMATED SUICIDE (ART. 253)"),
	RESPONSIBILITY(59,"RESPONSIBILITY OF PARTICIPANTS IN A DUEL IF ONLY PHYSICAL INJURIES ARE INFLICTED OR NO PHYSICAL INJURIES HAVE BEEN INFLICTED (ART. 260)"),
	LESS_PHYSICAL_INJURY(60,"LESS SERIOUS PHYSICAL INJURIES (ART. 265)"),
	SLIGHT_PHYSICAL_MALTREATMENT(61,"SLIGHT PHYSICAL INJURIES AND MALTREATMENT (ART. 266)"),
	UNLAWFUL_ARREST(62,"UNLAWFUL ARREST (ART. 269)"),
	HOME_ABANDONMENT(63,"INDUCING A MINOR TO ABANDON HIS/HER HOME (ART. 271)"),
	ABANDONMENT_VICTIMS(64,"ABANDONMENT OF A PERSON IN DANGER AND ABANDONMENT OF ONE�S OWN VICTIM (ART. 275)"),
	ABANDONMENT_MINOR(65,"ABANDONING A MINOR (A CHILD UNDER SEVEN [7] YEARS OLD) (ART. 276)"),
	ABANDONMENT_PARENT(66,"ABANDONMENT OF A MINOR BY PERSON ENTRUSTED WITH HIS/HER CUSTODY; INDIFFERENCE OF PARENTS (ART. 277)"),
	QUALIFIED_TRESPASS(67,"QUALIFIED TRESSPASS TO DWELLING (WITHOUT THE USE OF VIOLENCE AND INTIMIDATION) (ART. 280)"),
	OTHER_TRESSPASS(68,"OTHER FORMS OF TRESSPASS (ART. 281)"),
	OTHER_LIGHT_THREATS(69,"OTHER LIGHT THREATS (ART. 285)"),
	GRAVE_COERCION(70,"GRAVE COERCION (ART. 286)"),
	LIGHT_COERCION(71,"LIGHT COERCION (ART. 287)"),
	OTHER_COERCION(72,"OTHER SIMILAR COERCIONS (COMPULSORY PURCHASE OF MERCHANDISE AND PAYMENT OF WAGES BY MEANS OF TOKENS) (ART. 288)"),
	VIOLENCE_THREATS(73,"FORMATION, MAINTENANCE AND PROHIBITION OF COMBINATION OF CAPITAL OR LABOR THROUGH VIOLENCE OR THREATS (ART. 289)"),
	DISCOVERING_SECRETS(74,"DISCOVERING SECRETS THROUGH SEIZURE AND CORRESPONDENCE (ART. 290)"),
	REVEALING_SECRETS(75,"REVEALING SECRETS WITH ABUSE OF AUTHORITY (ART. 291)"),
	THEFT(76,"THEFT (ART. 309)"),
	QUALIFIED_THEFT(77,"QUALIFIED THEFT (ART. 310)"),
	OCCUPATION_PROPERTY(78,"OCCUPATION OF REAL PROPERTY OR USURPATION OF REAL RIGHTS IN PROPERTY (ART 312)"),
	ALTERING_BOUNDERIES(79,"ALTERING BOUNDARIES OR LANDMARKS (ART. 313)"),
	SWINDLING(80,"SWINDLING OR ESTAFA (ART. 315)"),
	OTHER_SWINDLING(81,"OTHER FORMS OF SWINDLING (ART. 316)"),
	SWINDLING_MINOR(82,"SWINDLING A MINOR (ART. 317)"),
	OTHER_DECIETS(83,"OTHER DECEITS (ART. 318)"),
	MORTGAGE_PROPERTY(84,"REMOVAL, SALE OR PLEDGE OF MORTGAGED PROPERTY (ART. 319)"),
	MISCHIEF(85,"SPECIAL CASES OF MALICIOUS MISCHIEF (ART 328)"),
	OTHER_MISCHIEF(86,"OTHER MISCHIEFS (ART. 329)"),
	SIMPLE_SEDUCTION(87,"SIMPLE SEDUCTION (ART. 338)"),
	LASCIVIOUSNESS(88,"ACTS OF LASCIVIOUSNESS WITH THE CONSENT OF THE OFFENDED PARTY (ART 339)"),
	THREATING_TO_COMPENSATION(89,"THREATENING TO PUBLISH AND OFFER TO PREVENT SUCH PUBLICATION FOR COMPENSATION (ART. 356)"),
	PROHIBITING_PUBLICATION(90,"PROHIBITING PUBLICATION OF ACTS REFERRED TO IN THE COURSE OF OFFICIAL PROCEEDINGS (ART. 357)"),
	INCRIMINATING_INNOCENT(91,"INCRIMINATING INNOCENT PERSONS (ART. 363)"),
	INTRIGUING_HONOR(92,"INTRIGUING AGAINST HONOR (ART. 364)"),
	ISSUING_NO_FUNDS(93,"ISSUING CHECKS WITHOUT SUFFICIENT FUNDS (BP 22)"),
	FENCING_STOLEN_PROPERTIES(94,"FENCING OF STOLEN PROPERTIES (PD 1612)");
	
	private int id;
	private String name;
	
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	private CaseKind(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public static String typeName(int id){
		for(CaseKind type : CaseKind.values()){
			if(id==type.getId()){
				return type.getName();
			}
		}
		return CaseKind.UNPAID_ACCOUNT.getName();
	}
	public static int typeId(String name){
		for(CaseKind type : CaseKind.values()){
			if(name.equalsIgnoreCase(type.getName())){
				return type.getId();
			}
		}
		return CaseKind.UNPAID_ACCOUNT.getId();
	}
	

}