package net.gegy1000.slyther.client.game;

import net.gegy1000.slyther.client.SlytherClient;
import net.gegy1000.slyther.game.*;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    public SlytherClient client;

    public String name;
    public int id;
    public float posX;
    public float posY;
    public Skin skin;
    public int er;
    public float pr;
    public float pma;
    public int eyeColor;
    public float eca;
    public int ppa;
    public int ppc;
    public boolean antenna;
    public boolean oneEye;
    public float swell;
    public float atba;
    public int atc1;
    public int atc2;
    public boolean atwg;
    public float atia;
    public boolean antennaBottomRotate;
    public float[] antennaX;
    public float[] antennaY;
    public float[] antennaVelocityX;
    public float[] antennaVelocityY;
    public float[] atax;
    public float[] atay;
    public int blbx;
    public int blby;
    public int blbw;
    public int blbh;
    public float bsc;
    public float blba;
    public int ebi;
    public int ebiw;
    public int ebih;
    public int ebisz;
    public int epi;
    public int epiw;
    public int epih;
    public int episz;
    public SkinColor[] rbcs;
    public SkinDetails skinDetails;
    public SkinColor color; // color value
    public int fnfr = 0;
    public int na;
    public float chl;
    public float tsp;
    public int sfr;
    public float scale;
    public float moveSpeed;
    public float accelleratingSpeed; // Fast speed?
    public float msp;
    public float[] fxs;
    public float[] fys;
    public float[] fchls;
    public int fpos;
    public int ftg;
    public float fx;
    public float fy;
    public float fchl;
    public float[] fas;
    public int fapos;
    public int fatg;
    public float fa;
    public float ehang;
    public float wehang;
    public int ehl;
    public int msl;
    public double fam;
    public float ang;
    public float eang;
    public float wang;
    public float rex;
    public float rey;
    public float speed;
    public SnakePoint lnp; // Tail point or Head point (Last point entry)
    public List<SnakePoint> points;
    public int sct;
    public int flpos;
    public float[] fls;
    public float fl;
    public int fltg;
    public double tl;
    public double cfl;
    public float scang;
    public float spang;
    public float deadAmt;
    public float aliveAmt;
    public boolean mouseDown;
    public boolean wasMouseDown;
    public boolean dead;
    public int turningDirection;
    public int edir;
    public float sep;
    public float wsep;
    public boolean isInView;
    public boolean antennaShown;
    public String antennaTexture;

    public Snake(SlytherClient client, int id, float x, float y, Skin skin, float angle, List<SnakePoint> points) {
        this.client = client;
        this.id = id;
        posX = x;
        posY = y;
        setSkin(skin);
        na = 1;
        scale = 1.0F;
        moveSpeed = client.NSP1 + client.NSP2 * scale;
        accelleratingSpeed = moveSpeed + 0.1F;
        msp = client.NSP3;
        fxs = new float[SlytherClient.RFC];
        fys = new float[SlytherClient.RFC];
        fchls = new float[SlytherClient.RFC];
        fas = new float[SlytherClient.AFC];
        ehang = angle;
        wehang = angle;
        ehl = 1;
        msl = 42;
        ang = angle;
        eang = angle;
        wang = angle;
        speed = 2;

        if (points != null) {
            lnp = points.get(points.size() - 1);
            this.points = points;
            sct = points.size();
            if (points.get(0).dying) {
                sct--;
            }
        } else {
            this.points = new ArrayList<>();
        }

        fls = new float[SlytherClient.LFC];
        tl = sct + fam;
        cfl = tl;
        scang = 1;
        deadAmt = 0;
        aliveAmt = 0;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
        er = 6;
        pr = 3.5F;
        pma = 2.3F;
        eyeColor = 0xFFFFFF;
        eca = 0.75F;
        ppa = 1;

        SkinDetails details = SkinHandler.INSTANCE.getDetails(skin);

        SkinColor[] pattern = new SkinColor[] { SkinColor.values()[skin.ordinal() % SkinColor.values().length] };

        if (details != null) {
            antenna = details.hasAntenna;
            atc1 = details.antennaPrimaryColor;
            atc2 = details.antennaSecondaryColor;
            atwg = details.atwg;
            atia = details.atia;
            antennaBottomRotate = details.abrot;
            int antennaLength = details.antennaLength;
            antennaX = new float[antennaLength];
            antennaY = new float[antennaLength];
            antennaVelocityX = new float[antennaLength];
            antennaVelocityY = new float[antennaLength];
            atax = new float[antennaLength];
            atay = new float[antennaLength];
            for (int i = 0; i < antennaLength; i++) {
                antennaX[i] = posX;
                antennaY[i] = posY;
            }
            blbx = details.blbx;
            blby = details.blby;
            blbw = details.blbw;
            blbh = details.blbh;
            bsc = details.bsc;
            blba = details.blba;
            eyeColor = details.eyeColor;
            eca = details.eca;
            oneEye = details.oneEye;
            ebiw = details.ebiw;
            ebih = details.ebih;
            ebisz = details.ebisz;
            epiw = details.epiw;
            epih = details.epih;
            episz = details.episz;
            pma = details.pma;
            swell = details.swell;
            antennaTexture = details.antennaTexture;

            pattern = details.pattern;
            skinDetails = details;
        }

        rbcs = pattern;
        color = pattern[0];
    }

    //Set new length
    public void snl() {
        double tl = this.tl;
        this.tl = sct + fam;
        tl = this.tl - tl;
        int flpos = this.flpos;
        for (int i = 0; i < SlytherClient.LFC; i++) {
            fls[flpos] -= tl * SlytherClient.LFAS[i];
            flpos++;
            if (flpos >= SlytherClient.LFC) {
                flpos = 0;
            }
        }
        fl = fls[this.flpos];
        fltg = SlytherClient.LFC;
        if (this == client.player) {
            client.wumsts = true;
        }
    }

    public void update(float vfr, float vfrb, float vfrb2) {
        float turnSpeed = client.MAMU * vfr * scang * spang;
        float moveAmount = speed * vfr / 4;
        if (moveAmount > msl) {
            moveAmount = msl;
        }
        if (client.allowUserInput) {
            if (this == client.player) {
                boolean prev = mouseDown;
                mouseDown = Mouse.isButtonDown(0) || Mouse.isButtonDown(1);
                if (prev != mouseDown) {
                    wasMouseDown = prev;
                }
            }
        }
        if (!dead) {
            if (tsp != speed) {
                if (tsp < speed) {
                    tsp += 0.3F * vfr;
                    if (tsp > speed) {
                        tsp = speed;
                    }
                } else {
                    tsp -= 0.3F * vfr;
                    if (tsp < speed) {
                        tsp = speed;
                    }
                }
            }
            if (tsp > accelleratingSpeed) {
                sfr += (tsp - accelleratingSpeed) * vfr * 0.021F;
            }
            if (fltg > 0) {
                float h = vfrb;
                if (h > fltg) {
                    h = fltg;
                }
                fltg -= h;
                for (int i = 0; i < h; i++) {
                    fl = fls[flpos];
                    fls[flpos] = 0;
                    flpos++;
                    if (flpos >= SlytherClient.LFC) {
                        flpos = 0;
                    }
                }
            } else {
                if (fltg == 0) {
                    fltg = -1;
                    fl = 0;
                }
            }
            cfl = tl + fl;
        }
        if (turningDirection == 1) {
            ang -= turnSpeed;
            if (ang < 0 || ang >= SlytherClient.PI_2) {
                ang %= SlytherClient.PI_2;
            }
            if (ang < 0) {
                ang += SlytherClient.PI_2;
            }
            float h = (float) ((wang - ang) % SlytherClient.PI_2);
            if (h < 0) {
                h += SlytherClient.PI_2;
            }
            if (h > Math.PI) {
                h -= SlytherClient.PI_2;
            }
            if (h > 0) {
                ang = wang;
                turningDirection = 0;
            }
        } else if (turningDirection == 2) {
            ang += turnSpeed;
            if (ang < 0 || ang >= SlytherClient.PI_2) {
                ang %= SlytherClient.PI_2;
            }
            if (ang < 0) {
                ang += SlytherClient.PI_2;
            }
            float h = (float) ((wang - ang) % SlytherClient.PI_2);
            if (h < 0) {
                h += SlytherClient.PI_2;
            }
            if (h > Math.PI) {
                h -= SlytherClient.PI_2;
            }
            if (h < 0) {
                ang = wang;
                turningDirection = 0;
            }
        } else {
            ang = wang;
        }
        if (ehl != 1) {
            ehl += 0.03F * vfr;
            if (ehl >= 1) {
                ehl = 1;
            }
        }
        SnakePoint point = points.get(points.size() - 1);
        if (point != null) {
            wehang = (float) Math.atan2(posY + fy - point.posY - point.fy + point.eby * (1.0F - ehl), posX + fx - point.posX - point.fx + point.ebx * (1.0F - ehl));
        }
        if (!dead) {
            if (ehang != wehang) {
                float h = (float) ((wehang - ehang) % SlytherClient.PI_2);
                if (h < 0) {
                    h += SlytherClient.PI_2;
                }
                if (h > Math.PI) {
                    h -= SlytherClient.PI_2;
                }
                if (h < 0) {
                    edir = 1;
                } else {
                    if (h > 0) {
                        edir = 2;
                    }
                }
            }
        }
        if (edir == 1) {
            ehang -= 0.1F * vfr;
            if (ehang < 0 || ehang >= SlytherClient.PI_2) {
                ehang %= SlytherClient.PI_2;
            }
            if (ehang < 0) {
                ehang += SlytherClient.PI_2;
            }
            float h = (float) ((wehang - ehang) % SlytherClient.PI_2);
            if (h < 0) {
                h += SlytherClient.PI_2;
            }
            if (h > Math.PI) {
                h -= SlytherClient.PI_2;
            }
            if (h > 0) {
                ehang = wehang;
                edir = 0;
            }
        } else if (edir == 2) {
            ehang += 0.1F * vfr;
            if (ehang < 0 || ehang >= SlytherClient.PI_2) {
                ehang %= SlytherClient.PI_2;
            }
            if (ehang < 0) {
                ehang += SlytherClient.PI_2;
            }
            float h = (float) ((wehang - ehang) % SlytherClient.PI_2);
            if (h < 0) {
                h += SlytherClient.PI_2;
            }
            if (h > Math.PI) {
                h -= SlytherClient.PI_2;
            }
            if (h < 0) {
                ehang = wehang;
                edir = 0;
            }
        }
        if (!dead) {
            posX += Math.cos(ang) * moveAmount;
            posY += Math.sin(ang) * moveAmount;
            chl += moveAmount / msl;
        }
        if (vfrb > 0) {
            for (int pointIndex = points.size() - 1; pointIndex >= 0; pointIndex--) {
                point = points.get(pointIndex);
                if (point.dying) {
                    point.da += 0.0015F * vfrb;
                    if (point.da > 1) {
                        points.remove(pointIndex);
                        point.dying = false;
                    }
                }
                if (point.eiu > 0) {
                    int fx = 0;
                    int fy = 0;
                    int cm = point.eiu - 1;
                    for (int qq = cm; qq >= 0; qq--) {
                        point.efs[qq] = (int) (point.ems[qq] == 2 ? point.efs[qq] + vfrb2 : point.efs[qq] + vfrb);
                        int h = point.efs[qq];
                        if (h >= SlytherClient.HFC) {
                            if (qq != cm) {
                                point.exs[qq] = point.exs[cm];
                                point.eys[qq] = point.eys[cm];
                                point.efs[qq] = point.efs[cm];
                                point.ems[qq] = point.ems[cm];
                            }
                            point.eiu--;
                            cm--;
                        } else {
                            fx += point.exs[qq] * SlytherClient.HFAS[h];
                            fy += point.eys[qq] * SlytherClient.HFAS[h];
                        }
                    }
                    point.fx = fx;
                    point.fy = fy;
                }
            }
        }
        float ex = (float) (Math.cos(eang) * pma);
        float ey = (float) (Math.sin(eang) * pma);
        if (rex < ex) {
            rex += vfr / 6.0F;
            if (rex > ex) {
                rex = ex;
            }
        }
        if (rey < ey) {
            rey += vfr / 6.0F;
            if (rey > ey) {
                rey = ey;
            }
        }
        if (rex > ex) {
            rex -= vfr / 6;
            if (rex < ex) {
                rex = ex;
            }
        }
        if (rey > ey) {
            rey -= vfr / 6;
            if (rey < ey) {
                rey = ey;
            }
        }
        if (vfrb > 0) {
            if (ftg > 0) {
                float h = vfrb;
                if (h > ftg) {
                    h = ftg;
                }
                ftg -= h;
                for (int i = 0; i < h; i++) {
                    fx = fxs[fpos];
                    fy = fys[fpos];
                    fchl = fchls[fpos];
                    fxs[fpos] = 0;
                    fys[fpos] = 0;
                    fchls[fpos] = 0;
                    fpos++;
                    if (fpos >= SlytherClient.RFC) {
                        fpos = 0;
                    }
                }
            } else if (ftg == 0) {
                ftg = -1;
                fx = 0;
                fy = 0;
                fchl = 0;
            }
            if (fatg > 0) {
                float h = vfrb;
                if (h > fatg) {
                    h = fatg;
                }
                fatg -= h;
                for (int qq = 0; qq < h; qq++) {
                    fa = fas[fapos];
                    fas[fapos] = 0;
                    fapos++;
                    if (fapos >= SlytherClient.AFC) {
                        fapos = 0;
                    }
                }
            } else if (fatg == 0) {
                fatg = -1;
                fa = 0;
            }
        }
        if (dead) {
            deadAmt += 0.02F * vfr;
            if (deadAmt >= 1.0F) {
                client.snakes.remove(this);
            }
        } else {
            if (aliveAmt != 1) {
                aliveAmt += 0.015F * vfr;
                if (aliveAmt > 1.0F) {
                    aliveAmt = 1.0F;
                }
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Integer) {
            return id == (Integer) object;
        } else if (object instanceof Short) {
            return id == (Short) object;
        } else if (object instanceof Snake) {
            return id == ((Snake) object).id;
        }
        return false;
    }
}