package com.mrpowergamerbr.millennium.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.imageio.ImageIO;

import org.jooby.Request;
import org.jooby.Response;

import com.mrpowergamerbr.millennium.Millennium;

public class TretaNewsGenerator {
	public static ArrayList<String> titleBef = new ArrayList<String>();
	public static List<String> objects;
	public static List<String> emotions;
	public static List<String> social;
	public static List<String> randomYt;
	public static List<String> randomDescriptions;
	public static List<String> randomGame;
	public static List<String> randomAvatars;

	static {
		titleBef.add("{@user} desabafa após polêmica com piada sobre {@object}");
		titleBef.add("{@user} é expulsa do {@social}");
		titleBef.add("{@user} faz piada com {@object} e sofre {@emotion}");
		titleBef.add("{@user} reclama do {@object}");
		titleBef.add("{@user} pretende sair do {@social}!?");
		titleBef.add("{@user} gera polêmica com vídeo");
		titleBef.add("{@user} vs Garota Conservadora");
		titleBef.add("{@user} tem {@object} roubado");
		titleBef.add("{@user} diz que vai dançar nua se chegar a 500K");
		titleBef.add("{@user} tem  roubada");
		titleBef.add("{@user} leva Golpe na Internet e perde placa de 100K inscritos");
		titleBef.add("{@user} leva Golpe na Internet e perde placa de 100K inscritos");
		titleBef.add("{@user} vai gravar vídeo pornô quando chegar a 1 milhão");
		titleBef.add("{@user} reclama de taxistas");
		titleBef.add("{@user} causa incêndio ao fritar hamburguer");
		titleBef.add("{@user} é xingado no meio da rua");
		titleBef.add("{@user} troca farpas por causa de vídeo de salgadinhos chips");
		titleBef.add("{@user} quase explode casa ao soltar Rojão");
		titleBef.add("{@user} é despejado de novo");
		titleBef.add("{@user} gera furdúncio com vídeo de salgadinhos");
		titleBef.add("{@user} critíca vídeos");
		titleBef.add("{@user} é invadida por ladrões");
		titleBef.add("Ladrão invade casa de Streamer AO VIVO!");
		titleBef.add("{@user} reclama de fãs invadindo sua privacidade");
		titleBef.add("{@user} sofre {@emotion} após queimar placa");
		titleBef.add("{@user} recebe reclamações de Clickbait");
		titleBef.add("{@user} queima {@object} com maçarico e gera furdúncio");
		titleBef.add("{@user} sofre {@emotion} após entrevistar {@object}");
		titleBef.add("{@user} tem vídeo com cenas de nudez removido do {@social}");
		titleBef.add("Felipe Neto critica {@user}");
		titleBef.add("Cellbit critica {@user}");
		titleBef.add("RezendeEvil e {@user} reclamam do {@object}");
		titleBef.add("{@user} e {@youtuber-1} reclamam do {@object}");
		titleBef.add("{@user} grava vídeo com {@object} e se acidenta");
		titleBef.add("{@user} grava vídeo com {@object} e se acidenta");
		titleBef.add("{@user} é vítima de assalto");
		titleBef.add("{@user} se envolve em polêmica e responde");
		titleBef.add("{@user} tem problemas e para canal");
		titleBef.add("{@user} posta vídeo de ataque com faca");
		titleBef.add("{@user} desmaia em livestream");
		titleBef.add("{@user} é atingido por {@object}");
		titleBef.add("{@user} posta vídeo com nudes");
		titleBef.add("{@user} é assaltado ao sair do colégio");
		titleBef.add("{@user} é golpeado com {@object} durante gravação de pegadinha");
		titleBef.add("{@user} tranca {@object} em caixa e gera polêmica");
		titleBef.add("{@user}, {@youtuber-1} e {@youtuber-2} causam furdúncio");
		titleBef.add("VenomExtreme, {@user} e Fluffy causam furdúncio");
		titleBef.add("{@user} é agredido na rua");
		titleBef.add("{@object} do {@user} vira piscininha");
		titleBef.add("Bloco de gelo explode na cara de {@user}");
		titleBef.add("Cellbit e Coelho criticam {@user}");
		titleBef.add("Youtuber corre atrás de ladrão que roubou seu {@object}");
		titleBef.add("{@user} NÃO deleta canal e trolla todo mundo");
		titleBef.add("{@user} deleta canal e trolla todo mundo");
		titleBef.add("{@user} é preso em pegadinha");
		titleBef.add("Youtubers criticam Youtube Rewind 2016");
		titleBef.add("{@user} ironiza rumores de ter dado \"ataque de estrelismo\"");
		titleBef.add("{@user} se acidenta ao gravar gameplay");
		titleBef.add("{@object} explode na cara de {@user}");
		titleBef.add("{@user} critica jogadores famosos de League of Legends");
		titleBef.add("{@user} critica jogadores famosos de DOTA");
		titleBef.add("{@user} critica jogadores famosos de Overwatch");
		titleBef.add("{@user} critica jogadores famosos de Minecraft");
		titleBef.add("{@user} critica jogadores famosos de Counter Strike");
		titleBef.add("{@user} se envolve em acidente de trânsito");
		titleBef.add("{@user} passa cheetos no olho");
		titleBef.add("{@user} posta música e sofre hate");
		titleBef.add("{@youtuber-1} e {@youtuber-2} criticam youtubers");
		titleBef.add("{@user} voltou?");
		titleBef.add("{@user} leva soco de fã do  {@youtuber-1}");
		titleBef.add("{@user} responde {@youtuber-1}");
		titleBef.add("Vídeo do {@youtuber-1} deixa {@user} e outros Youtubers estupefatos");
		titleBef.add("{@user} cai e quebra clavícula");
		titleBef.add("{@user} foi sabotado?");
		titleBef.add("{@user} invade palco e é alfinetado por {@youtuber-1}");
		titleBef.add("{@user} vai QUEBRAR sua placa de 1 milhão de inscritos!?");
		titleBef.add("{@user} teve canal DELETADO!? ");
		titleBef.add("{@user} desmente boato");
		titleBef.add("{@user} e {@youtuber-1} trocam farpas no {@social}");
		titleBef.add("{@user} sofre sequestro relâmpago");
		titleBef.add("{@youtuber-1} teve canal hackeado!?");
		titleBef.add("{@user} foi realmente possuída!?");
		titleBef.add("Ela se cortou!?");
		titleBef.add("Ele se cortou!?");
		titleBef.add("{@user} critica vídeos de espíritos");
		titleBef.add("{@object} gera furdúncio entre youtubers");
		titleBef.add("Bugs estão saindo de controle?");
		titleBef.add("{@user} é possuída ao jogar tabuleiro Ouija AO VIVO!?");
		titleBef.add("{@youtuber-1} é possuída ao jogar {@object} AO VIVO!?");
		titleBef.add("{@youtuber-1} usa {@object} para flagrar traição do {@user} e posta vídeo no {@social}");
		titleBef.add("Terremoto atinge casa de {@user} AO VIVO");
		titleBef.add("{@user} é hackeado de novo");
		titleBef.add("{@user} é surpreendido por traficantes");
		titleBef.add("{@user} reclama de FC's");
		titleBef.add("{@user} leva SOCO NA CARA");
		titleBef.add("{@user} mostra os peitos e é banida");
		titleBef.add("{@youtuber-1} mostra a bunda e é banido");
		titleBef.add("{@user} recebe presente de stalker!?");
		titleBef.add("{@user} possuído pega FOGO sozinho!?");
		titleBef.add("{@youtuber-1} filma {@user} possuído!?");
		titleBef.add("{@user} falou mal do {@youtuber-1} pelas costas");
		titleBef.add("{@user} leva cotovelada de repórter");
		titleBef.add("Youtuber enterra {@object} vivo e gera polêmica");
		titleBef.add("{@user} flagra espírito em vídeo!?");
		titleBef.add("{@user} faz 100 camadas de agulhadas no rosto e é censurado");
		titleBef.add("Atrasados perdem prova e geram furdúncio");
		titleBef.add("{@youtuber-1} faz camarote para assistir");
		titleBef.add("{@youtuber-1} recebe críticas após sensualizar em show e responde!");
		titleBef.add("Youtube vai mudar os comentários?");
		titleBef.add("{@user} trolla namorada e gera polêmica");
		titleBef.add("{@youtuber-1} alfineta {@youtuber-2}!?");
		titleBef.add("{@user} sofre ATAQUE hater");
		titleBef.add("{@user} é alfinetado após derrota na copa de {@game}");
		titleBef.add("Editores se revoltam contra {@user}");
		titleBef.add("{user} leva STRIKE");
		titleBef.add("{@user} APANHOU na rua");
		titleBef.add("{@user} é chamado de arregão e responde");
		titleBef.add("{@user} luta com touro em livestream e vai parar no hospital");
		titleBef.add("{@user} encontra {@object} assassino!?");
		titleBef.add("{@user} é alvo de novo ataque hater");
		titleBef.add("{@user} perde mais de 30 mil inscritos");
		titleBef.add("{@user} e outros youtubers comentam polêmica");
		titleBef.add("{@user} cospe na boca de {@object} e causa revolta na internet");
		titleBef.add("{@user} se revolta contra o {@social}");
		titleBef.add("{@user} dá corte de giro na cara do {@youtuber-1}");
		titleBef.add("{@user} critica estado atual do {@social}");
		titleBef.add("{@youtuber-1} dá block no {@youtuber-2}!?");
		titleBef.add("{@user} é PERIGOSO!?");
		titleBef.add("{@user} está planejando seu templo");
		titleBef.add("{@user} gera revolta de youtubers de MotoVlog e motociclistas");
		titleBef.add("Vídeos de 100 camadas geram furdúncio");
		titleBef.add("{@user} pede desculpas a {@youtuber-1}");
		titleBef.add("{@object} misterioso ataca {@user}!");
		titleBef.add("{@object} misterioso MATA {@user}!");
		titleBef.add("{@youtuber-1} MATA {@user} AO VIVO!");
		titleBef.add("{@user} grava acidente de carro AO VIVO!");
		titleBef.add("{@user} grava acidente de carro AO VIVO!");
		titleBef.add("{@user} pega FOGO e leva STRIKE");
		titleBef.add("{@user} pega FOGO e MORRE");
		titleBef.add("{@user} é agredido e filma tudo");
		titleBef.add("{@user} descobre quem é o {@object} que está atacando sua casa!");
		titleBef.add("{@user} responde Hater");
		titleBef.add("{@user} leva choque de tazer e desmaia");
		titleBef.add("\"Me tiraram pra Judas\" diz {@user}");
		titleBef.add("Grupo de famosos fala mal do {@user} no {@social}!?");
		titleBef.add("{@user} vai sair na rua gritando G2A!?");
		titleBef.add("{@user} recebe doação de 10 mil reais e passa mal,");
		titleBef.add("{@user} é alvo de trollagem");
		titleBef.add("{@youtuber-1} se livra do strike aplicado por {@user}");
		titleBef.add("{@user} faz limpa de mendigos de comentários");
		titleBef.add("Espírito fala \"{@user}\" em vídeo do {@youtuber-1}!?");
		titleBef.add("{@user} foi TRANCADO dentro da sua própria casa!");
		titleBef.add("{@user} vai banir mendigos de comentários!?");
		titleBef.add("{@user} gera grande furdúncio");

		objects = Arrays.asList(
				"YouTube Brasil",
				"YouTube",
				"PewDiePie",
				"Deus",
				"Jeová",
				"Garota",
				"Garoto",
				"Twitter",
				"Snapchat",
				"Facebook",
				"Discord",
				"Skype",
				"Instagram",
				"Periscope",
				"Orkut",
				"Google+",
				"YouTube",
				"Tumblr",
				"Carro",
				"Placa de 100k",
				"Vin Diesel",
				"Ferro Quente",
				"Pombo",
				"Faca",
				"Gato",
				"Cachorro",
				"Geladeira",
				"Drone",
				"iPhone",
				"Samsung Galaxy",
				"Bomba",
				"Tabuleiro Ouija",
				"Palhaço");

		emotions = Arrays.asList(
				"hate",
				"alegria",
				"amor",
				"felicidade");

		social = Arrays.asList(
				"Snapchat",
				"Facebook",
				"Twitter",
				"Discord",
				"Skype",
				"Instagram",
				"Periscope",
				"Orkut",
				"Google+",
				"YouTube",
				"Tumblr");

		randomYt = Arrays.asList(
				"Felipe Neto",
				"Kéfera",
				"Castanhari",
				"PokeyBR",
				"Leon",
				"Monark",
				"Feromonas",
				"VenomExtreme",
				"Hannah Meyers",
				"RezendeEvil",
				"AuthenticGames",
				"BRKsEDU",
				"MoonKase",
				"Stux",
				"Fluffy",
				"PortugaPC",
				"Spok",
				"PewDiePie",
				"MrPoladoful",
				"GatoGalactico",
				"Coelho",
				"MixReynold",
				"ForeverPlayer",
				"Everson Zoio",
				"Bluezão",
				"Não Salvo",
				"Mussoumano",
				"Aruan Felix");

		randomDescriptions = Arrays.asList(
				"Kéfera usa Snapchat para comentar polêmica, Haru tem conta banida do Facebook",
				"Kéfera sofreu hate por causa de piada com Deus, RezendeEvil reclamou do youtube Brasil novamente",
				"Castanhari do Canal Nostalgia diz que youtube está virando piada, Pedro Strapasson gera furdúncio com vídeo polêmico",
				"Felipe Neto e Garota Conservadora geraram furdúncio, Pedro Strapasson foi assaltado e gravou tudo",
				"Youtuber Paola Holmes diz que vai dançar funk sem roupa quando chegar a 500K, PortugaPC possivelmente teve placa roubada",
				"Neox e Eagle do canal Neagle tiveram suas placas de 100 mil inscritos roubadas",
				"Youtuber promete gravar vídeo pornô quando chegar em 1 milhão de inscritos, EduKof e PortugaPC reclamam do serviço de taxistas",
				"Streamer tentou fazer live cozinhando mas acabou dando errado, Youtuber Spok foi xingado jogar minecraft",
				"Enaldinho e PokeyBR trocam farpas no twitter depois de postarem vídeos parecidos",
				"Youtuber quase se acidentou ao soltar fogos de artifício, Pewdiepie foi despejado pela segunda vez",
				"PokeyBR responde polêmica do seu vídeo de chips, Cellbit fez vídeo trollando o estado atual do youtube",
				"Youtuber teve vários pertences roubados em sua casa, Pyong Lee diz que vai largar canal no Twitter",
				"Youtubers T3ddy e Clone reclamam de falta de privacidade, Ladrão tenta roubar streamer ao vivo",
				"Youtuber Inemafoo responde críticas após queimar placa, Contente é alvo de reclamações após vídeo com título polêmico",
				"Youtuber Inemafoo queimou sua placa do Youtube com um maçarico e gerou polêmica",
				"Youtuber Carol Moreira relata assédio de Vin Diesel durante entrevista, Felipe Neto e Cellbit criticam Youtube Brasil",
				"Novo clipe de Clarice Falcão com cenas de nudez é removido do Youtube, Felipe Neto criticou Ju Nogueira",
				"RezendeEvil e Felipe Neto reclamam dos bugs do Youtube",
				"Vlad do Área Secreta se acidentou em seu último vídeo, SanInPlay foi assaltado quando gravava um clipe na praia",
				"Enaldinho se envolveu em furdúncio no Twitter, Drawn Mask perdeu PC e vai parar canal por um bom tempo",
				"Robson do Irmãos fuinha postou vídeo do desafio em que foi golpeado com faca, Streamer desmaiou ao vivo",
				"Youtuber se acidenta por causa de pombo durante motovlog, RezendeEvil e Contente reclamam do Youtube",
				"Zoie Burgher postou vídeo com nudes e gerou polêmica, Youtuber foi assaltado ao sair da escola",
				"Youtuber foi ferido por uma faca durante gravação de um vídeo",
				"Streamer acusada de maus tratos contra animais, PokeyBR, stux777 e MoonKase geram furdúncio no Twitter",
				"Youtuber foi agredido na rua, Geladeira do Rato Borrachudo quebra e forma bolha de água na gaveta de legumes",
				"Cellbit, Coelho, Luba e MrPoladoful comentam sobre a polêmica do Pewdiepie, Mega bloco de gelo explode no rosto de Youtuber",
				"Youtuber teve drone furtado e corre atrás do ladrão, MixReynold anuncia que não vai quebrar placa",
				"Pewdiepie deleta canal secundário e deixa algumas pessoas revoltadas, Youtuber foi preso em pegadinha",
				"Felipe Neto, Leon do Coisa de Nerd, Gusta e Kéfera criticaram o Youtube Rewind 2016 no Twitter",
				"Kéfera anda de helicóptero e brinca com piloto: 'Fala para ninguém me incomodar', Youtuber foi atropelada ao atravessar a rua",
				"Rato Borrachudo teve que ir para o hospital após retirar gesso para gravar gameplay, ForeverPlayer criticou youtubers no Twitter");

		randomGame = Arrays.asList("Minecraft",
				"Overwatch",
				"DOTA",
				"League of Legends",
				"Counter-Strike");

		randomAvatars = Arrays.asList(
				"https://yt3.ggpht.com/-zRMDUjopMak/AAAAAAAAAAI/AAAAAAAAAAA/cXAODFnSfQQ/s176-c-k-no-mo-rj-c0xffffff/photo.jpg",
				"https://yt3.ggpht.com/-M7_xnCYVo04/AAAAAAAAAAI/AAAAAAAAAAA/B067rIwWq3o/s176-c-k-no-mo-rj-c0xffffff/photo.jpg",
				"https://yt3.ggpht.com/-YENFbHgKY4g/AAAAAAAAAAI/AAAAAAAAAAA/M35kguQVZfM/s176-c-k-no-mo-rj-c0xffffff/photo.jpg",
				"https://yt3.ggpht.com/-MokimQWVRYs/AAAAAAAAAAI/AAAAAAAAAAA/kGBW7ISel90/s176-c-k-no-mo-rj-c0xffffff/photo.jpg",
				"https://yt3.ggpht.com/-4D606al42iY/AAAAAAAAAAI/AAAAAAAAAAA/DHqZkHbQyJQ/s176-c-k-no-mo-rj-c0xffffff/photo.jpg",
				"https://yt3.ggpht.com/--ihXSjx7V8c/AAAAAAAAAAI/AAAAAAAAAAA/lglLtJPbpYg/s176-c-k-no-mo-rj-c0xffffff/photo.jpg",
				"https://i.ytimg.com/vi/tVlQJ_XBZFY/hqdefault.jpg?custom=true&w=246&h=138&stc=true&jpg444=true&jpgq=90&sp=68&sigh=qx1CZ4K1lLCqJNOUCA6JnVCEtmg",
				"https://yt3.ggpht.com/-e2MAPIZYc_k/AAAAAAAAAAI/AAAAAAAAAAA/7JLG3L0EGYs/s176-c-k-no-mo-rj-c0xffffff/photo.jpg",
				"https://yt3.ggpht.com/-1LWkxlQsCoM/AAAAAAAAAAI/AAAAAAAAAAA/21vbu7XZYhE/s176-c-k-no-mo-rj-c0xffffff/photo.jpg",
				"https://yt3.ggpht.com/-bIkhJe7-vdk/AAAAAAAAAAI/AAAAAAAAAAA/m5AyW98M-CY/s176-c-k-no-mo-rj-c0xffffff/photo.jpg");
	}

	public static File generate(Request req, Response res) {
		String str1 = TretaNewsGenerator.randomYt.get(Millennium.rand.nextInt(0, TretaNewsGenerator.randomYt.size() - 1));
		String str2 = TretaNewsGenerator.randomYt.get(Millennium.rand.nextInt(0, TretaNewsGenerator.randomYt.size() - 1));
		BufferedImage avatar = null;
		String url1 = TretaNewsGenerator.randomAvatars.get(Millennium.rand.nextInt(0, TretaNewsGenerator.randomAvatars.size() - 1));
		String url2 = TretaNewsGenerator.randomAvatars.get(Millennium.rand.nextInt(0, TretaNewsGenerator.randomAvatars.size() - 1));
		try {
			URL imageUrl = new URL(url1);
			HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
			connection.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0");
			avatar = ImageIO.read(connection.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedImage avatar2 = null;
		try {
			URL imageUrl = new URL(url2);
			HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
			connection.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0");
			avatar2 = ImageIO.read(connection.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedImage tretaCheck = null;
		try {
			tretaCheck = ImageIO.read(new File("/home/servers/whistler/tretacheck.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BufferedImage top = new BufferedImage(238, 138, BufferedImage.TYPE_INT_ARGB);

		BufferedImage treta = null;
		try {
			treta = ImageIO.read(new File("/home/servers/whistler/tretasmall.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedImage novo = null;
		try {
			novo = ImageIO.read(new File("/home/servers/whistler/tretanovo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Image tempRI1 = avatar.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
		Image tempRI2 = avatar2.getScaledInstance(128, 128, Image.SCALE_SMOOTH);

		Image resizedImage = tempRI1.getScaledInstance(tempRI1.getWidth(null), 138, Image.SCALE_SMOOTH);
		Image resizedImage2 = tempRI2.getScaledInstance(tempRI2.getWidth(null), 138, Image.SCALE_SMOOTH);

		top.getGraphics().drawImage(resizedImage, 0, 0, null);
		top.getGraphics().drawImage(resizedImage2, 119, 0, null);

		top.getGraphics().drawImage(treta, 0, 0, null);


		BufferedImage youtube = new BufferedImage(655, 138, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) youtube.getGraphics();
		youtube.getGraphics().setColor(Color.WHITE);
		youtube.getGraphics().fillRect(0, 0, 655, 158);

		youtube.getGraphics().drawImage(top, 0, 0, null);

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


		g2d.setColor(new Color(22, 138, 213));
		Font font = new Font("Arial", Font.PLAIN, 18);
		g2d.setFont(font);

		String t = titleBef.get(Millennium.rand.nextInt(0, titleBef.size() - 1));
		String object = objects.get(Millennium.rand.nextInt(0, objects.size() - 1));
		String emotion = emotions.get(Millennium.rand.nextInt(0, emotions.size() - 1));
		String social = TretaNewsGenerator.social.get(Millennium.rand.nextInt(0, TretaNewsGenerator.social.size() - 1));

		String rndYt1_1 = TretaNewsGenerator.randomYt.get(Millennium.rand.nextInt(0, TretaNewsGenerator.randomYt.size() - 1));
		String rndYt2_1 = TretaNewsGenerator.randomYt.get(Millennium.rand.nextInt(0, TretaNewsGenerator.randomYt.size() - 1));
		String rndYt3_1 = TretaNewsGenerator.randomYt.get(Millennium.rand.nextInt(0, TretaNewsGenerator.randomYt.size() - 1));
		String rndYt4_1 = TretaNewsGenerator.randomYt.get(Millennium.rand.nextInt(0, TretaNewsGenerator.randomYt.size() - 1));
		String rndYt5_1 = TretaNewsGenerator.randomYt.get(Millennium.rand.nextInt(0, TretaNewsGenerator.randomYt.size() - 1));
		String game = TretaNewsGenerator.randomGame.get(Millennium.rand.nextInt(0, TretaNewsGenerator.randomGame.size() - 1));

		t = t.replace("{@user}", str1);
		t = t.replace("{@object}", object);
		t = t.replace("{@emotion}", emotion);
		t = t.replace("{@social}", social);
		t = t.replace("{@youtuber-1}", rndYt1_1);
		t = t.replace("{@youtuber-2}", rndYt2_1);
		t = t.replace("{@youtuber-3}", rndYt3_1);
		t = t.replace("{@youtuber-4}", rndYt4_1);
		t = t.replace("{@youtuber-5}", rndYt5_1);
		t = t.replace("{@game}", game);

		if (Millennium.rand.nextInt(0, 12) != 5) {
			String t2 = titleBef.get(Millennium.rand.nextInt(0, titleBef.size() - 1));
			String object2 = objects.get(Millennium.rand.nextInt(0, objects.size() - 1));
			String emotion2 = emotions.get(Millennium.rand.nextInt(0, emotions.size() - 1));
			String social2 = TretaNewsGenerator.social.get(Millennium.rand.nextInt(0, TretaNewsGenerator.social.size() - 1));

			rndYt1_1 = TretaNewsGenerator.randomYt.get(Millennium.rand.nextInt(0, TretaNewsGenerator.randomYt.size() - 1));
			rndYt2_1 = TretaNewsGenerator.randomYt.get(Millennium.rand.nextInt(0, TretaNewsGenerator.randomYt.size() - 1));
			rndYt3_1 = TretaNewsGenerator.randomYt.get(Millennium.rand.nextInt(0, TretaNewsGenerator.randomYt.size() - 1));
			rndYt4_1 = TretaNewsGenerator.randomYt.get(Millennium.rand.nextInt(0, TretaNewsGenerator.randomYt.size() - 1));
			rndYt5_1 = TretaNewsGenerator.randomYt.get(Millennium.rand.nextInt(0, TretaNewsGenerator.randomYt.size() - 1));

			t2 = t2.replace("{@user}", str2);
			t2 = t2.replace("{@object}", object2);
			t2 = t2.replace("{@emotion}", emotion2);
			t2 = t2.replace("{@social}", social2);
			t2 = t2.replace("{@youtuber-1}", rndYt1_1);
			t2 = t2.replace("{@youtuber-2}", rndYt2_1);
			t2 = t2.replace("{@youtuber-3}", rndYt3_1);
			t2 = t2.replace("{@youtuber-4}", rndYt4_1);
			t2 = t2.replace("{@youtuber-5}", rndYt5_1);
			t2 = t2.replace("{@game}", game);

			t += ", " + t2;
		}

		StringBuilder sb = new StringBuilder(t);

		int i = 0;
		while ((i = sb.indexOf(" ", i + 35)) != -1) {
			sb.replace(i, i + 1, "\n");
		}

		String title = sb.toString();

		if (title.length() > 90) {
			title = title.substring(0, 90) + "...";
		}

		int checkY = drawString(g2d, title, 244, 0);

		checkY += 6;

		g2d.drawImage(tretaCheck, 240, checkY, null);

		checkY += 14;

		g2d.setColor(new Color(118, 118, 118));
		font = new Font("Arial", Font.PLAIN, 12);
		g2d.setFont(font);

		String texto = Millennium.rand.nextInt(1, 24) + " horas atrás ● " + Millennium.rand.nextInt(0, 1000000) + " visualizações";

		drawString(g2d, texto, 244, checkY);

		checkY += 14;

		String descricao = TretaNewsGenerator.randomDescriptions.get(Millennium.rand.nextInt(0, TretaNewsGenerator.randomDescriptions.size() - 1));

		StringBuilder sbx = new StringBuilder(descricao);

		i = 0;
		while ((i = sbx.indexOf(" ", i + 60)) != -1) {
			sbx.replace(i, i + 1, "\n");
		}

		String sbDescricao = sbx.toString();

		if (sbDescricao.length() > 90) {
			sbDescricao = sbDescricao.substring(0, 90) + "...";
		}

		checkY = drawString(g2d, sbDescricao, 244, checkY);

		checkY += 14;

		g2d.drawImage(novo, 244, checkY, null);

		res.header("Pragma", "public");
		long cacheExpire2 = 0L;
		SimpleDateFormat df1 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
		df1.setTimeZone(TimeZone.getTimeZone("GMT"));
		long expire1 = System.currentTimeMillis() + cacheExpire2 * 1000L;
		String expires = df1.format(new Date(expire1)) + " GMT";
		res.header("Expires", expires);
		res.header("Content-Type", "image/png");
		String name = "tretanews" + (new Date().toString()) + ".png";
		
		res.header("Content-Disposition", "inline; filename=\"" + name + ".png\"");
		
		byte[] data1 = null;

		try {
			ByteArrayOutputStream cacheExpire1 = new ByteArrayOutputStream();
			ImageIO.write(youtube, "png", cacheExpire1);
			cacheExpire1.flush();
			data1 = cacheExpire1.toByteArray();
			cacheExpire1.close();
		} catch (Exception var17) {
			var17.printStackTrace();
		}
		
		try {
			try {
				res.download(name, new ByteArrayInputStream(data1));
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception var16) {
			var16.printStackTrace();
		}
		return null;
	}

	private static int drawString(Graphics g, String text, int x, int y) {
		int checkY = 0;
		for (String line : text.split("\n")) {
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
			checkY = y;
		}
		return checkY;
	}
}
